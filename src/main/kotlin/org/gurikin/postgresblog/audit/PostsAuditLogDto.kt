package org.gurikin.postgresblog.audit

import org.gurikin.postgresblog.posts.PostResponseDto
import java.time.LocalDateTime

data class PostsAuditLogDto(
    override val operation: OperationType,
    override val logTime: LocalDateTime,
    override val tableName: String,
    override val oldValue: PostResponseDto?,
    override val newValue: PostResponseDto?
) : AuditLogDto<PostResponseDto>(operation, logTime, tableName, oldValue, newValue)