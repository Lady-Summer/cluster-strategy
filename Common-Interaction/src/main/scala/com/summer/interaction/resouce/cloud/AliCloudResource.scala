package com.summer.interaction.resouce.cloud

import java.util

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.ecs.model.v20140526.{DeleteInstanceRequest, RunInstancesRequest}
import com.aliyuncs.exceptions.{ClientException, ServerException}
import com.aliyuncs.profile.DefaultProfile
import com.summer.cloud.CloudConfig.{ClusterConfig, InstanceConfig}
import com.summer.cloud._
import com.summer.config.{DefaultConfig, InstanceRequestConfig}
import com.summer.connector.http.HttpCode
import com.summer.enums.StatusCode
import com.summer.response.{ClusterResponse, InstanceResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object AliCloudResource extends CloudResource {

  override def createCluster(cloudConfig: CloudConfig)(defaultConfig: DefaultConfig): Future[either] = {
    val clusterConfig = cloudConfig.clusterConfig.get
    val instanceConfig = cloudConfig.instanceConfig.get
    Future[Either[CloudException, ClusterResponse]] {
      createFromConfig(instanceConfig, clusterConfig)(defaultConfig) match {
        case Success(response) =>
          // TODO instance response
          val clusterResp = new ClusterResponse(HttpCode.SUCCESS.getCode, "Create cluster success",
            new InstanceResponse(HttpCode.CREATED.getCode, "Start creating cluster", StatusCode.PENDING))
          // TODO sync with PostgreSQL
          clusterResp.setId(response.getRequestId)
          Right(clusterResp)
        case Failure(exception) =>
          exception match {
            case e: CloudException => Left(e)
            case e: ServerException =>
              val failure = new CloudServerException()
              logger.error("Cloud Server has failure, errorCode: {}, errorMsg: {}", e.getErrCode, e.getErrMsg)
              failure.message = e.getErrMsg
              Left(failure)
            case e: ClientException =>
              val failure = new CloudClientException
              failure.message = e.getErrMsg
              Left(failure)
            case e: Exception =>
              val failure = new CloudCreateFailure
              failure.message = e.getMessage
              Left(failure)
          }
      }
    }
  }

  override def destroyCluster(clusterId: String)(defaultConfig: DefaultConfig): Future[either] = {
    // TODO write business logic after Ananka interface ready
    val deleteReq = new DeleteInstanceRequest()
    deleteReq.setSysRegionId(defaultConfig.regionId)
    deleteAll(deleteReq, clusterId)(defaultConfig)
  }

  private def deleteAll(deleteReq: DeleteInstanceRequest,
                        clusterId: String)(defaultConfig: DefaultConfig) = {

    Future {
      val instanceIdSet = Set[String]()
      val client = createAcsClient(defaultConfig)
      instanceIdSet.foreach(id => {
        deleteReq.setInstanceId(id)
        client.getAcsResponse(deleteReq)
      })
      Right(new ClusterResponse(HttpCode.SUCCESS.getCode, s"Destroying cluster $clusterId",
        new InstanceResponse(HttpCode.SUCCESS.getCode, "Start destroying instances", StatusCode.PENDING)))
    }.recover {
      case e: ServerException =>
        val exception = new CloudServerException()
        exception.code = Integer.valueOf(e.getErrCode)
        exception.message = e.getErrMsg
        Left(exception)
      case e: ClientException =>
        val exception = new CloudClientException
        exception.code = Integer.valueOf(e.getErrCode)
        exception.message = e.getErrMsg
        Left(exception)
      case Exception => Left(new CloudDestroyFailure)
    }

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
    request.setDataDisks(createDataDiskList(config))
    request
  }

  private def createFromConfig(instanceConfig: InstanceConfig, clusterConfig: ClusterConfig)
                           (defaultConfig: DefaultConfig)(implicit ex: ExecutionContext) = {
    Try {
      // TODO validate parameters
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

  override def expandCluster(config: CloudConfig)(defaultConfig: DefaultConfig): Future[either] = {
    Future[either]
  }
}
