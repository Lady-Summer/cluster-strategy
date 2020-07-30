package com.summer.cloud

case class InstanceException(code: Int, description: String) extends Throwable

class InternetChargeTypeNotDefinedException extends InstanceException(100, "Internet Charge Type should be defined")

class InstanceTypeNotDefinedException extends InstanceException(200, "Instance Type should be provided")

class InstanceChargeTypeNotDefinedException extends InstanceException(300, "Instance Charge Type should be defined")

class InvalidInstanceRequestException extends InstanceException(400, "Invalid instance operation request")
