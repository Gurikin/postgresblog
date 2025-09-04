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
                if (ue != null) {
                    // Пользователь найден
                    Mono.just(AuthResultDto(login = ue.login, status = true))
                } else {
                    // Пользователь не найден
                    Mono.empty()
                }
            }
            .switchIfEmpty(Mono.just(AuthResultDto(login = login.login, status = false, message = "User with login ${login.login} not found")))
    }

    fun signUp(signUpDto: SignUpDto): Mono<AuthResultDto> {
        val result = checkIfUserExists(signUpDto)
            .flatMap {
                if (it) {
                    Mono.just(
                        getFalseAuthResult(signUpDto.login, signUpDto.email)
                    )
                } else {
                    userRepository.save(Users(login = signUpDto.login, email = signUpDto.email, userId = null))
                        .map { u -> AuthResultDto(login = u.login, status = true) }
                }
            }.onErrorResume { _ ->
                Mono.just(getFalseAuthResult(signUpDto.login, signUpDto.email))
            }
        return result
    }

    private fun checkIfUserExists(signUpDto: SignUpDto): Mono<Boolean> {
        return userRepository.findByLoginOrEmail(signUpDto.login, signUpDto.email).hasElement()
    }

    private fun getFalseAuthResult(login: String, email: String): AuthResultDto {
        return AuthResultDto(
            login = login,
            status = false,
            message = "Login $login or email $email is busy. Try another login/email"
        )
    }
}