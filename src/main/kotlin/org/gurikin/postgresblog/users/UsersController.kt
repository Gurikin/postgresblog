package org.gurikin.postgresblog.users

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user")
class UsersController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody login: LoginDto): Mono<AuthResultDto> {
        return authService.authenticate(login)
    }
}