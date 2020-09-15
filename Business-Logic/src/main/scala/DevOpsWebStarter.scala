import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DevOpsWebStarter


object DevOpsWebStarter {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[DevOpsWebStarter], args:_*)
  }
}
