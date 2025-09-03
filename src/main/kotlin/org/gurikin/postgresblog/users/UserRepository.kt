package org.gurikin.postgresblog.users

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface UserRepository : R2dbcRepository<UserEntity, Long> {
    @Query(
        """
        select u from UserEntity u where u.login = :login or u.email = :login
    """
    )
    fun findByLogin(login: String): Mono<UserEntity?>
}