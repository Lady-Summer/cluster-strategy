package com.summer.interaction.resouce.cloud

import com.summer.cloud.{CloudConfig, CloudException}
import com.summer.config.DefaultConfig
import com.summer.log.StrictLogging
import com.summer.response.ClusterResponse

import scala.concurrent.Future

trait CloudResource extends StrictLogging {

  protected type either = Either[CloudException, ClusterResponse]

  def createCluster(cloudConfig: CloudConfig)(defaultConfig: DefaultConfig): Future[either]

  def destroyCluster(clusterId: String)(defaultConfig: DefaultConfig): Future[either]

//  def expandCluster(config: CloudConfig)(defaultConfig: DefaultConfig): Future[either]

}
