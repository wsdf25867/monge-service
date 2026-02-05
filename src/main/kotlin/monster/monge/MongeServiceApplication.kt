package monster.monge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongeApplication

fun main(args: Array<String>) {
    runApplication<MongeApplication>(*args)
}
