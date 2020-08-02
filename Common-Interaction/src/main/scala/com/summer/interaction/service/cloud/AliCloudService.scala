package com.summer.interaction.service.cloud

import com.summer.cloud.CloudConfig
import com.summer.config.{DefaultConfig, InstanceRequestConfig}
import com.summer.connector.web.BaseResponse
import com.summer.interaction.resouce.cloud.AliCloudResource
import com.summer.request.ClusterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.concurrent.ExecutionContext.Implicits.global

@Service
class AliCloudService extends CloudService {

  @Autowired
  private val defaultConfig: DefaultConfig

  def addNode(instanceReq: InstanceRequestConfig, clusterId: Int)(req: ClusterRequest): BaseResponse[String] = {
    val cloudConfig = CloudConfig(
      CloudConfig.ClusterConfig(
        Option(clusterId), req.size, req.clusterName, req.cloudType), new CloudConfig.InstanceConfig)
    AliCloudResource.expandCluster(cloudConfig)(defaultConfig).map {
      // TODO fault tolerance
    }
  }

  def createCluster(config: InstanceRequestConfig)(req: ClusterRequest): BaseResponse[String] =  {
    val clusterConfig = CloudConfig.ClusterConfig(Option.empty, req.size, req.clusterName, req.cloudType)
    val instanceConfig = new CloudConfig.InstanceConfig
    AliCloudResource.createCluster(
      CloudConfig(clusterConfig, instanceConfig))(defaultConfig)
      .map {
      // TODO fault tolerance
    }
  }

  def destroyCluster(clusterId: Int): BaseResponse[String] = {
    AliCloudResource.destroyCluster(clusterId)(defaultConfig).map {
      //TODO fault tolerance
    }
  }

}
