package service1

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication @EnableDiscoveryClient
class Service1App
fun main(args: Array<String>) { SpringApplication(Service1App::class.java).run(*args) }