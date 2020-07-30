package com.summer.interaction.resouce.cloud

import com.summer.cloud.CloudConfig
import com.summer.config.DefaultConfig
import com.summer.log.StrictLogging

trait CloudResource extends StrictLogging {

  def createCluster(cloudConfig: CloudConfig)(defaultConfig: DefaultConfig)

  def destroyCluster(clusterId: Int)(defaultConfig: DefaultConfig)

  def expandCluster(config: CloudConfig)(defaultConfig: DefaultConfig)

}
