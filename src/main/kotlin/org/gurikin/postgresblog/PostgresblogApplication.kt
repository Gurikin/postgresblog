package org.gurikin.postgresblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostgresblogApplication

fun main(args: Array<String>) {
    runApplication<PostgresblogApplication>(*args)
}
