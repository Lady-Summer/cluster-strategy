package com.summer.interaction.resouce.cloud

import java.util

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.ecs.model.v20140526.RunInstancesRequest
import com.aliyuncs.profile.DefaultProfile
import com.summer.cloud.CloudConfig.{ClusterConfig, InstanceConfig}
import com.summer.cloud.{CloudConfig, ClusterException}
import com.summer.config.{DefaultConfig, InstanceRequestConfig}
import com.summer.response.ClusterResponse

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

object AliCloudResource extends CloudResource {

  private type either = Either[ClusterException, ClusterResponse]

  def createCluster(cloudConfig: CloudConfig)(defaultConfig: DefaultConfig): Future[either] = {
    // TODO validate parameters
    val clusterConfig = cloudConfig.clusterConfig.get
    val instanceConfig = cloudConfig.instanceConfig.get
    Future[Either[ClusterException, ClusterResponse]] {
      createFromConfig(instanceConfig, clusterConfig)(defaultConfig) match {
        case Success(response) =>
      }
    }
  }

  def destroyCluster(clusterId: Int)(defaultConfig: DefaultConfig): Future[either] = {
    Future[Either[ClusterException, ClusterResponse]] {

    }
  }

  def expandCluster(config: CloudConfig)(defaultConfig: DefaultConfig): Future[either] = {

  }

  private def createRunInstancesRequest(config: InstanceRequestConfig, defaultConfig: DefaultConfig) = {
    val request = new RunInstancesRequest
    request.setRegionId(defaultConfig.regionId)
    request.setImageId(config.instanceConfig.imageId)
    request.setInstanceType(config.instanceConfig.instanceType)
    request.setSecurityGroupId(defaultConfig.securityGroupId)
    request.setInstanceName(config.instanceConfig.instanceName)
    request.setInternetMaxBandwidthOut(config.internetConfig.maxBoundwidthOut)
    request.setInstanceChargeType(config.instanceConfig.instanceChargeType)
    val dataDiskList = createDataDiskList(config)
    request.setDataDisks(dataDiskList)
    request
  }

  private def createFromConfig(instanceConfig: InstanceConfig, clusterConfig: ClusterConfig)
                           (defaultConfig: DefaultConfig)(implicit ex: ExecutionContext) = {
    Try {
      val client = createAcsClient(defaultConfig)
      val runInstancesRequest = createRunInstancesRequest(instanceConfig, defaultConfig)
      runInstancesRequest.setAmount(clusterConfig.size)
      client.getAcsResponse(runInstancesRequest)
    }
  }

  private def createDataDiskList(config: InstanceRequestConfig) = {
    val dataDiskList = new util.ArrayList[RunInstancesRequest.DataDisk]
    (0 to config.dataDiskConfig.number).foreach(_ => dataDiskList.add(createDataDisk(config)))
    dataDiskList
  }

  private def createDataDisk(config: InstanceRequestConfig) = {
    val dataDisk = new RunInstancesRequest.DataDisk
    dataDisk.setSize(config.dataDiskConfig.size)
    dataDisk.setCategory(config.dataDiskConfig.category)
    dataDisk.setDeleteWithInstance(config.dataDiskConfig.deleteWithInstance)
    dataDisk.setDescription(config.dataDiskConfig.description)
    dataDisk.setDiskName(config.dataDiskConfig.name)
    dataDisk.setSnapshotId(config.dataDiskConfig.snapshotId)
    dataDisk
  }

  private def createAcsClient(defaultConfig: DefaultConfig) = {
    val profile = DefaultProfile.getProfile(defaultConfig.regionId, defaultConfig.accessKey, defaultConfig.accessSecret)
    new DefaultAcsClient(profile)
  }

}
