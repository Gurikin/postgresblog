package org.gurikin.postgresblog

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

@Import(TestcontainersConfiguration::class)
@SpringBootTest
@Profile(value = ["test"])
class PostgresblogApplicationTests {

    @Test
    fun contextLoads() {
    }

}
