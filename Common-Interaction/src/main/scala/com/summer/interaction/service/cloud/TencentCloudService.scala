package com.summer.interaction.service.cloud
import com.summer.config.InstanceRequestConfig
import com.summer.request.ClusterRequest

class TencentCloudService extends CloudService {
  override def addNode(instanceReq: InstanceRequestConfig, clusterId: String)(req: ClusterRequest): Unit = {

  }

  override def createCluster(config: InstanceRequestConfig)(req: ClusterRequest): Unit = {}

  override def destroyCluster(clusterId: String): Unit = {

  }
}
