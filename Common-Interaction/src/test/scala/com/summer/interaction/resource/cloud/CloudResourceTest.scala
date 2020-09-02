package com.summer.interaction.resource.cloud

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.ecs.model.v20140526.{RunInstancesRequest, RunInstancesResponse}
import com.aliyuncs.exceptions.{ClientException, ServerException}
import com.summer.cloud.CloudConfig
import com.summer.cloud.CloudConfig.ClusterConfig
import com.summer.config.DefaultConfig
import com.summer.enums.configType.CloudType
import com.summer.interaction.resouce.cloud.AliCloudResource
import org.junit.Assert._
import org.junit.{Before, Test}
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


class CloudResourceTest  {

  private val defaultConfig: DefaultConfig = new DefaultConfig

  private val cloudConfig: CloudConfig = new CloudConfig

  @Before
  def setUp(): Unit = {
    defaultConfig.accessKey = ""
    cloudConfig.clusterConfig = Option(ClusterConfig(Option("1111"), 2, "test", CloudType.ALICLOUD.getDescription))
    cloudConfig.instanceConfig = Option(new CloudConfig.InstanceConfig)
  }

  @Test
  def testCreateClusterSuccess(): Unit = {
    val client = spy(classOf[DefaultAcsClient])
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
    Await.result(responseFuture, 3.seconds)
  }

  @Test
  def testCreateClusterServerFailure(): Unit = {
    val client = spy(classOf[DefaultAcsClient])
    val exception = new ServerException("100", "server exception")
    doThrow(exception).when(client).getAcsResponse(any(classOf[RunInstancesRequest]))
    val responseFuture = AliCloudResource.createCluster(cloudConfig)(defaultConfig)
    responseFuture.map {
      case Right(_) =>
        fail("Should not return response")
      case Left(e) =>
        assertEquals(e.message, exception.getErrMsg)
    }
    Await.result(responseFuture, 3.seconds)
  }

  @Test
  def testCreateClusterClientFailure(): Unit = {
    val client = spy(classOf[DefaultAcsClient])
    val exception = new ClientException("100", "client exception")
    doThrow(exception).when(client).getAcsResponse(any(classOf[RunInstancesRequest]))
    val responseFuture = AliCloudResource.createCluster(cloudConfig)(defaultConfig)
    responseFuture.map {
      case Right(_) =>
        fail("Should not return response")
      case Left(e) =>
        assertEquals(e.message, exception.getErrMsg)
    }
    Await.result(responseFuture, 3.seconds)
  }

}
