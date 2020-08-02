package com.summer.interaction.resource.cloud

import java.util.concurrent.TimeUnit

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.ecs.model.v20140526.{RunInstancesRequest, RunInstancesResponse}
import com.aliyuncs.profile.DefaultProfile
import com.summer.cloud.CloudConfig
import com.summer.config.DefaultConfig
import com.summer.interaction.resouce.cloud.AliCloudResource
import org.junit.Assert._
import org.junit.{Before, Test}
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


class CloudResourceTest  {

  private val defaultConfig: DefaultConfig = new DefaultConfig

  private val cloudConfig: CloudConfig = new CloudConfig

  @Before
  def setUp(): Unit = {
    defaultConfig.accessKey = ""
  }

  @Test
  def testCreateClusterSuccess(): Unit = {
    val client = mock(classOf[DefaultAcsClient])
    doReturn(client).when(DefaultProfile)
      .getProfile(defaultConfig.regionId, defaultConfig.accessKey, defaultConfig.accessSecret)
    val resp = new RunInstancesResponse
    resp.setRequestId("1111")
    doReturn(resp).when(client).getAcsResponse(any(classOf[RunInstancesRequest]))
    val responseFuture = AliCloudResource.createCluster(cloudConfig)(defaultConfig)
    responseFuture.map {
      case Right(response) =>
        assertEquals(response.getId, resp.getRequestId)
      case Left(_) =>
        fail()
    }
    Await.result(responseFuture, Duration.create(3L, TimeUnit.SECONDS))
  }

}
