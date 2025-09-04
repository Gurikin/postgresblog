package org.gurikin.postgresblog.users

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    fun signIn(login: LoginDto): Mono<AuthResultDto> {
        return userRepository.findByLogin(login.login)
            .flatMap { ue ->
                when (ue == null) {
                    true -> Mono.just(AuthResultDto(login = login.login, status = true))
                    else -> Mono.just(AuthResultDto(login = login.login, status = false))
                }
            }
    }

    fun signUp(signUpDto: SignUpDto): Mono<AuthResultDto> {
        val result = checkIfUserExists(signUpDto)
            .flatMap {
                if (it) {
                    Mono.just(
                        AuthResultDto(
                            login = signUpDto.login,
                            status = false,
                            message = "Login ${signUpDto.login} or email ${signUpDto.email} is busy. Try another login/email"
                        )
                    )
                } else {
                    userRepository.save(UserEntity(login = signUpDto.login, email = signUpDto.email, userId = null))
                        .map { u -> AuthResultDto(login = u.login, status = true) }
                }
            }
        return result
    }

    private fun checkIfUserExists(signUpDto: SignUpDto): Mono<Boolean> {
        return userRepository.findByLoginOrEmail(signUpDto.login, signUpDto.email).hasElement()
    }
}