import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(Array("com.summer"))
class CommonInteractionStarter

object CommonInteractionStarter {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[CommonInteractionStarter], args:_*)
}
