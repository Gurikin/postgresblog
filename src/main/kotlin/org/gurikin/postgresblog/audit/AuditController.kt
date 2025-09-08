package org.gurikin.postgresblog.audit

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping()
class AuditController(
    private val auditService: AuditService,
) {
    @GetMapping("/private/audit-info/{postId}")
    fun getAllPostsByUserId(@PathVariable("postId") postId: Long): Mono<ResponseEntity<List<PostsAuditLogDto?>>> {
        return auditService.audit(postId)
            .flatMap { posts -> Mono.just(ResponseEntity.ok(posts)) }
    }
}