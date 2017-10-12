package com.twocogs.homeassistant.pcmonitor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PcMonitorApplication

fun main(args: Array<String>) {
    SpringApplication.run(PcMonitorApplication::class.java, *args)
}