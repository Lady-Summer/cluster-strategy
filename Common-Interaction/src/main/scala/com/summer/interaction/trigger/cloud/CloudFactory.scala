package com.summer.interaction.trigger.cloud

import com.summer.enums.configType.CloudType
import com.summer.interaction.service.cloud.AliCloudService

object CloudFactory {

  private val cloudTypeMapper = Map(CloudType.ALICLOUD.getDescription -> new AliCloudService)

  def apply(cloudType: String) = {
    cloudTypeMapper(cloudType)
  }
}
