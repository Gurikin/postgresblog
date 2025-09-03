package org.gurikin.postgresblog.users

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    fun authenticate(login: LoginDto): Mono<AuthResultDto> {
        return userRepository.findByLogin(login.login)
            .flatMap { ue ->
                when (ue == null) {
                    true -> Mono.just(AuthResultDto(login = login.login, status = true))
                    else -> Mono.just(AuthResultDto(login = login.login, status = false))
                }
            }
    }
}