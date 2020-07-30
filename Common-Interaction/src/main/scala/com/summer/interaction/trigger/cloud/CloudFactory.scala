package com.summer.interaction.trigger.cloud

import com.summer.enums.configType.CloudType
import com.summer.interaction.service.cloud.AliCloudService

object CloudFactory {

  def apply(cloudType: String) = {
    cloudType match {
      case CloudType.ALICLOUD.getDescription => new AliCloudService
    }
  }
}
