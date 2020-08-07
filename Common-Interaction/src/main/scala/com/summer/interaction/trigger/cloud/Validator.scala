package com.summer.interaction.trigger.cloud

import com.summer.cloud.{InstanceChargeTypeNotDefinedException, InstanceException, InstanceTypeNotDefinedException, InternetChargeTypeNotDefinedException}
import com.summer.config.InstanceRequestConfig
import com.summer.log.StrictLogging

import scala.util.Try


object Validator extends StrictLogging {


  def validateInstanceConfig(data: InstanceRequestConfig)= {

    def validateInstanceConfig(configParams: InstanceRequestConfig) = {
      Option(configParams.instanceConfig.instanceType).fold {
        logger.error("Instance Type should be provided")
        throw new InstanceTypeNotDefinedException
      } {
        a =>
          logger.debug("Instance type is: {}", a)
          Option(configParams.instanceConfig.imageId)
            .fold (logger.debug("imageId is not defined."))(a => logger.debug("imageId is: {}", a))
          Option(configParams.instanceConfig.instanceChargeType).fold {
            logger.error("instance Charge type should be provided.")
            throw new InstanceChargeTypeNotDefinedException
          } {
            a => logger.debug("Instance charge type is: {}", a)
          }
      }
    }

    def validateInternetConfig(configParams: InstanceRequestConfig) = {
      Option(configParams.internetConfig.internetChargeType).fold {
        logger.error("Internet Charge Type Should be given.")
        throw new InternetChargeTypeNotDefinedException
      } {
        a => logger.debug("Internet Charge Type is: {}", a)
      }
    }


    def validate() = {
      Option(data).fold {
        logger.error("The request body of create must be set.")
        throw InstanceException(400, "Invalid request body. ")
      } {
        body =>
          Try {
            validateInstanceConfig(body)
            validateInternetConfig(body)
          }
      }

    }
    validate
  }

}
