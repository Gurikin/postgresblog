package org.gurikin.postgresblog.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchanges: AuthorizeExchangeSpec ->
                exchanges.anyExchange().permitAll()
            }
            .httpBasic()
            .and()
            .formLogin()
            .disable()
            .csrf().disable()
            .authenticationManager(alwaysSuccessAuthenticationProvider())
            .build()
    }

    @Bean
    fun alwaysSuccessAuthenticationProvider(): ReactiveAuthenticationManager {
        return AlwaysSuccessAuthenticationProvider()
    }
}

class AlwaysSuccessAuthenticationProvider : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        // Возвращаем успешную аутентификацию с ролью USER
        return Mono.just(AuthenticationResult(authentication.principal, listOf(SimpleGrantedAuthority("ROLE_USER"))))
    }
}

class AuthenticationResult(
    private val principal: Any?,
    private val listOf: List<SimpleGrantedAuthority>,
    private val isAuthenticated: Boolean = true,
) :
    Authentication {
    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.listOf.toMutableList()
    }

    override fun getCredentials(): Any {
        TODO("Not yet implemented")
    }

    override fun getDetails(): Any {
        TODO("Not yet implemented")
    }

    override fun getPrincipal(): Any {
        return "Good Principal"
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        TODO("Not yet implemented")
    }

}
