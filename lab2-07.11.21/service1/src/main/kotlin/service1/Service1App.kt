package service1

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Service1App
fun main(args: Array<String>) {
    with(SpringApplication(Service1App::class.java)) {
        setBannerMode(Banner.Mode.OFF)
        run(*args)
    }
}