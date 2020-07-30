package com.summer.interaction.trigger.cloud

import com.summer.cloud.InstanceException
import com.summer.connector.http.HttpCode
import com.summer.connector.web.BaseResponse
import com.summer.enums.configType.CloudType
import com.summer.interfaces.Trigger
import com.summer.log.StrictLogging
import com.summer.request.ClusterRequest
import org.springframework.web.bind.annotation._

import scala.util.{Failure, Success}

@RestController("/trigger/cluster")
class ClusterTrigger extends Trigger with StrictLogging {

  @PatchMapping("/add")
  override def add(@RequestBody request: ClusterRequest, @RequestParam("id") clusterId: Int): BaseResponse[String] = {
    Option(request.getData).fold {
      new BaseResponse(HttpCode.BAD_REQUEST.getCode,
        HttpCode.BAD_REQUEST.getMessage ,"The cluster request body should not be none")
    } {
      clusterReq =>
        Validator.validateInstanceConfig(clusterReq.getData) match {
          case Success(_) =>  CloudFactory(request.cloudType).addNode(clusterReq.getData, clusterId)(request)
          case Failure(exception) =>
            generateFaultInfo(exception)
        }
    }

  }

  @DeleteMapping("/destroy")
  override def destroy(@RequestParam("id") clusterId: Int): BaseResponse[String] = {
    logger.debug(s"Start destroying cluster, cluster id is: $clusterId")
    CloudFactory(CloudType.ALICLOUD.getDescription).destroyCluster(clusterId)
  }

  @PostMapping("/initiate")
  override def initiate(@RequestBody req: ClusterRequest) = {

    Option(req.getData).fold {
      new BaseResponse(HttpCode.BAD_REQUEST.getCode,
        HttpCode.BAD_REQUEST.getMessage ,"The request body should not be none")
    } {
      instanceReq =>
        Validator.validateInstanceConfig(instanceReq.getData) match {
          case Success(_) => CloudFactory(req.cloudType).createCluster(instanceReq.getData)(req)
          case Failure(exception) =>
            generateFaultInfo(exception)
        }
    }
  }

  private def generateFaultInfo(exception: Throwable) = {
    exception match {
      case e: Exception => new BaseResponse(HttpCode.SERVER_INTERNAL_ERROR.getCode,
        HttpCode.SERVER_INTERNAL_ERROR.getMessage, e.getMessage)
      case e: InstanceException => new BaseResponse(HttpCode.BAD_REQUEST.getCode,
        HttpCode.BAD_REQUEST.getMessage, e.description)
    }
  }
}
