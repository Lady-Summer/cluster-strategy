package com.summer.interaction.service.cloud

import com.google.gson.Gson
import com.summer.config.{DefaultConfig, InstanceRequestConfig}
import com.summer.connector.web.BaseResponse
import com.summer.log.StrictLogging
import com.summer.request.ClusterRequest

trait CloudService extends StrictLogging {

  // TODO initialize defaultConfig by calling Ananka interface
  protected val defaultConfig: DefaultConfig

  protected val gson = new Gson

  protected case class ClusterProperties(clusterId: String, clusterName: String)

  def addNode(instanceReq: InstanceRequestConfig, clusterId: String)(req: ClusterRequest): BaseResponse[String]

  def createCluster(config: InstanceRequestConfig)(req: ClusterRequest): BaseResponse[String]

  def destroyCluster(clusterId: String): BaseResponse[String]
}
