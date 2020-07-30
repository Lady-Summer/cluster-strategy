package com.summer.cloud

case class ClusterException(code: Int, message: String) extends Throwable

class ClusterDestroyFailure extends ClusterException(1100, "Destroy cluster failure")

class ClusterCreateFailure extends ClusterException(1101, "Create Cluster fail")

class ClusterExpandFailure extends ClusterException(1102, "Expand cluster node fail")
