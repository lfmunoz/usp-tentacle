package eco.usp.automate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AutomateApplication

fun main(args: Array<String>) {
	runApplication<AutomateApplication>(*args)
}
