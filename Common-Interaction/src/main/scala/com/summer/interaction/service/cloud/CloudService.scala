package com.summer.interaction.service.cloud

import com.summer.config.InstanceRequestConfig
import com.summer.log.StrictLogging
import com.summer.request.ClusterRequest

trait CloudService extends StrictLogging {

  def addNode(instanceReq: InstanceRequestConfig, clusterId: Int)(req: ClusterRequest)

  def createCluster(config: InstanceRequestConfig)(req: ClusterRequest)

  def destroyCluster(clusterId: Int)
}
