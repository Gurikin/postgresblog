package org.gurikin.postgresblog

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<PostgresblogApplication>().with(TestcontainersConfiguration::class).run(*args)
}
