package org.gurikin.postgresblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
class PostgresblogApplication

fun main(args: Array<String>) {
    runApplication<PostgresblogApplication>(*args)
}
