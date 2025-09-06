package org.gurikin.postgresblog.users

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    companion object {
        private val log = LoggerFactory.getLogger(AuthService::class.java)
    }

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
            .switchIfEmpty {
                log.info("User {} not found", login.login)
                Mono.just(AuthResultDto(login = login.login, status = false, message = "User with login ${login.login} not found"))
            }
            .onErrorResume {
                log.error(it.message, it)
                Mono.just(AuthResultDto(login = login.login, status = false, message = "User with login ${login.login} not found"))
            }
    }

    fun signUp(signUpDto: SignUpDto): Mono<AuthResultDto> {
        val result = checkIfUserExists(signUpDto)
            .flatMap {
                log.info("Try to save user {} to DB", signUpDto.login)
                userRepository.save(Users(login = signUpDto.login, email = signUpDto.email, userId = null))
                    .map { u -> AuthResultDto(login = u.login, status = true) }
            }
            .onErrorResume { error ->
                log.error(error.message, error)
                when (error) {
                    is DataIntegrityViolationException -> Mono.just(getFalseAuthResult(signUpDto.login, signUpDto.email))
                    else -> Mono.error(error)
                }

            }
        return result
    }

    private fun checkIfUserExists(signUpDto: SignUpDto): Mono<Boolean> {
        return userRepository.findByLoginOrEmail(signUpDto.login, signUpDto.email)
            .hasElement()
            .onErrorResume { Mono.just(true) }
    }

    private fun getFalseAuthResult(login: String, email: String): AuthResultDto {
        return AuthResultDto(
            login = login,
            status = false,
            message = getFalseAuthResultMessage(login, email)
        )
    }

    private fun getFalseAuthResultMessage(login: String, email: String): String {
        return "Login $login or email $email is busy. Try another login/email"
    }
}