package org.gurikin.postgresblog.audit

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuditService(
    private val auditLogRepository: AuditLogRepository,
    private val auditLogEntityToPostsAuditLogDtoMapper: AuditLogEntityToPostsAuditLogDtoMapper
) : IAuditService<List<PostsAuditLogDto?>> {
    override fun audit(sourceId: Long): Mono<List<PostsAuditLogDto?>> {
        return auditLogRepository.findAllByPostId(sourceId.toString())
            .map {
                log.info("Map {} to PostsAuditLogDto", it)
                auditLogEntityToPostsAuditLogDtoMapper.map(it)
            }
            .onErrorResume { error ->
                log.error("Error on get audit_log list by post_id = $sourceId. Message: {}", error.message, error)
                Mono.error(error)
            }
            .collectList()
    }

    companion object {
        private val log = LoggerFactory.getLogger(AuditService::class.java)
    }
}
