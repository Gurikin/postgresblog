package org.gurikin.postgresblog.audit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.gurikin.postgresblog.posts.Posts
import org.gurikin.postgresblog.posts.toPostResponseDto
import org.springframework.stereotype.Component

@Component
class AuditLogEntityToPostsAuditLogDtoMapper(
    private val objectMapper: ObjectMapper
) {
    fun map(auditLog: AuditLog): PostsAuditLogDto {
        return PostsAuditLogDto(
            operation = auditLog.operation,
            logTime = auditLog.createDttm,
            tableName = auditLog.tableName,
            oldValue = objectMapper.readValue<Posts>(auditLog.oldValue).toPostResponseDto(),
            newValue = objectMapper.readValue<Posts>(auditLog.newValue).toPostResponseDto(),
        )
    }
}