package com.summer.cloud

case class CloudException(var code: Int, var message: String) extends Throwable

class CloudDestroyFailure extends CloudException(1100, "Destroy cluster failure")

class CloudCreateFailure extends CloudException(1101, "Create Cluster fail")

class CloudExpandFailure extends CloudException(1102, "Expand cluster node fail")

class CloudServerException extends CloudException(1103, "Cloud Server has exception")

class CloudClientException extends CloudException(1104, "Cloud client has exception")

