package com.summer.interaction.service.cloud

import java.util.concurrent.TimeUnit

import com.summer.cloud.{CloudConfig, CloudException}
import com.summer.config.InstanceRequestConfig
import com.summer.connector.web.BaseResponse
import com.summer.interaction.resouce.cloud.AliCloudResource
import com.summer.request.ClusterRequest

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class AliCloudService extends CloudService {

  def addNode(instanceReq: InstanceRequestConfig, clusterId: Int)(req: ClusterRequest): BaseResponse[String] = {
    val cloudConfig = CloudConfig(
      CloudConfig.ClusterConfig(
        Option(clusterId), req.size, req.clusterName, req.cloudType), new CloudConfig.InstanceConfig)
    AliCloudResource.expandCluster(cloudConfig)(defaultConfig).map {
      // TODO fault tolerance
    }
  }

  def createCluster(config: InstanceRequestConfig)(req: ClusterRequest) =  {

    val clusterConfig = CloudConfig.ClusterConfig(Option.empty, req.size, req.clusterName, req.cloudType)
    val instanceConfig = new CloudConfig.InstanceConfig
    val responseFuture = AliCloudResource.createCluster(
      CloudConfig(clusterConfig, instanceConfig))(defaultConfig)
      .map {
      // TODO fault tolerance
        case Right(response) =>
          val clusterProperties = ClusterProperties(response.getId, response.getClusterName)
          new BaseResponse[String](response.code, response.message, gson.toJson(clusterProperties))
        case Left(exception) =>
          exception match {
            case e: CloudException =>
              new BaseResponse[String](e.code, "Create cluster failed", e.message)
            case e =>
              new BaseResponse[String](e.code, e.message, "")
          }
    }
    Await.result(responseFuture, Duration.create(5L, TimeUnit.SECONDS))
  }

  def destroyCluster(clusterId: Int): BaseResponse[String] = {
    AliCloudResource.destroyCluster(clusterId)(defaultConfig).map {
      //TODO fault tolerance
    }
  }

}
