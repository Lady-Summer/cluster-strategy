import org.springframework.boot.{SpringApplication, SpringBootConfiguration}

@SpringBootConfiguration
class CommonInteractionStarter {

  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[CommonInteractionStarter].getClasses, args)
  }
}
