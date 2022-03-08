package testpassword

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

const val NAMESPACE_URI = "http://testpassword/dtos"

@SpringBootApplication class Service1
fun main(args: Array<String>) { SpringApplication.run(Service1::class.java, *args) }