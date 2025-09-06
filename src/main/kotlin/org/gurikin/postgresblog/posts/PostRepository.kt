package org.gurikin.postgresblog.posts

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.query.Param
import reactor.core.publisher.Mono

interface PostRepository : R2dbcRepository<Posts, Long> {
    @Query("select * from posts p where p.user_id = :userId")
    fun findAllByUserId(@Param("userId") userId: Long): Mono<Posts?>
}