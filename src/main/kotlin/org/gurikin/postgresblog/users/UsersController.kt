package org.gurikin.postgresblog.users

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun login(@RequestBody login: LoginDto): Mono<ResponseEntity<AuthResultDto>> {
        return authService.signIn(login)
            .flatMap {
                when (it.status) {
                    true -> Mono.just(ResponseEntity.ok(it))
                    false -> Mono.just(
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(it)
                    )
                }
            }
    }

    @PostMapping("/signup")
    fun signup(@RequestBody signUpDto: SignUpDto): Mono<ResponseEntity<AuthResultDto>> {
        return authService.signUp(signUpDto)
            .flatMap {
                when (it.status) {
                    true -> Mono.just(ResponseEntity.ok(it))
                    false -> Mono.just(
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it)
                    )
                }
            }
    }
}