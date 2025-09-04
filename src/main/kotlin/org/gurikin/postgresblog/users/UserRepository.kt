package org.gurikin.postgresblog.users

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface UserRepository : R2dbcRepository<Users, Long> {
    @Query(
        """
        select * from users u where u.login = :login or u.email = :login
    """
    )
    fun findByLogin(login: String): Mono<Users?>

    @Query(
        """
        select u from users u where u.login = :login or u.email = :email
    """
    )
    fun findByLoginOrEmail(login: String, email: String): Mono<Users?>
}