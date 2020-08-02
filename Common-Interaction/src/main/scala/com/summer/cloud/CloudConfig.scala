package com.summer.cloud

import com.summer.config.InstanceRequestConfig

object CloudConfig {

  case class ClusterConfig(clusterId: Option[String], size: Int, name: String, cloudType: String)

  class InstanceConfig extends InstanceRequestConfig

  def apply(clusterConf: ClusterConfig, instanceConf: InstanceConfig) = new CloudConfig {
    clusterConfig = Option(clusterConf)
    instanceConfig = Option(instanceConf)
  }

}

class CloudConfig {

  var clusterConfig: Option[CloudConfig.ClusterConfig] = Option.empty

  var instanceConfig: Option[CloudConfig.InstanceConfig] = Option.empty
}
