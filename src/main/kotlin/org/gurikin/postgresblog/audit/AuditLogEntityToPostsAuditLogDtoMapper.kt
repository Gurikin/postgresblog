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
        val oldValue = if (auditLog.oldValue == null) null else objectMapper.readValue<Posts>(auditLog.oldValue).toPostResponseDto()
        val newValue = if (auditLog.newValue == null) null else objectMapper.readValue<Posts>(auditLog.newValue).toPostResponseDto()
        return PostsAuditLogDto(
            operation = OperationType.findByLetter(auditLog.operation),
            logTime = auditLog.createdDttm,
            tableName = auditLog.tableName,
            oldValue = oldValue,
            newValue = newValue,
        )
    }
}