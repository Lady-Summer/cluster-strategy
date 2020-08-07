package com.summer.interaction.service.cloud

import com.summer.cloud.{CloudClientException, CloudConfig, CloudDestroyFailure, CloudException, CloudServerException}
import com.summer.config.InstanceRequestConfig
import com.summer.connector.http.HttpCode
import com.summer.connector.web.BaseResponse
import com.summer.interaction.resouce.cloud.AliCloudResource
import com.summer.request.ClusterRequest

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class AliCloudService extends CloudService {

  def addNode(instanceReq: InstanceRequestConfig, clusterId: String)(req: ClusterRequest): BaseResponse[String] = {
    val cloudConfig = CloudConfig(
      CloudConfig.ClusterConfig(
        Option(clusterId), req.size, req.clusterName, req.cloudType), new CloudConfig.InstanceConfig)
    val responseFuture = AliCloudResource.expandCluster(cloudConfig)(defaultConfig).map {
      // TODO fault tolerance
      case Right(value) => new BaseResponse[String](HttpCode.CREATED.getCode,
        "add node successfully", gson.toJson(value))
    }
    Await.result(responseFuture, 5.seconds)
  }

  def createCluster(config: InstanceRequestConfig)(req: ClusterRequest) =  {

    val clusterConfig = CloudConfig.ClusterConfig(Option.empty, req.size, req.clusterName, req.cloudType)
    val instanceConfig = new CloudConfig.InstanceConfig
    val responseFuture = AliCloudResource.createCluster(
      CloudConfig(clusterConfig, instanceConfig))(defaultConfig)
      .map {
        case Right(response) =>
          // TODO write cluster info in to config center
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
    Await.result(responseFuture, 5.seconds)
  }

  def destroyCluster(clusterId: String): BaseResponse[String] = {
    val responseFuture = AliCloudResource.destroyCluster(clusterId)(defaultConfig).map {
      case Right(value) => new BaseResponse[String](HttpCode.ACCEPT.getCode,
        value.message, gson.toJson(value.data))
      case Left(exception) =>
        exception match {
          case e: CloudServerException => new BaseResponse[String](HttpCode.SERVER_FORBIDON.getCode,
            s"Fail to destroy cluster $clusterId", e.message)
          case e: CloudClientException => new BaseResponse[String](HttpCode.BAD_REQUEST.getCode,
            "Fail to destroy cluster $clusterId", e.message)
          case e: CloudDestroyFailure =>
            new BaseResponse[String](HttpCode.SERVER_INTERNAL_ERROR.getCode, "Internal Error", e.message)
        }

    }

    Await.result(responseFuture, 5.seconds)

  }

}
