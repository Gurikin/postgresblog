package org.gurikin.postgresblog.audit

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface AuditLogRepository : R2dbcRepository<AuditLog, Long> {
    @Query(
        """
        SELECT *
            FROM audit_log
            WHERE (old_value::json->'post_id' = :postId) OR (new_value::json->'post_id' = :postId)
    """
    )
    fun findAllByPostId(postId: Long): Flux<AuditLog>
}