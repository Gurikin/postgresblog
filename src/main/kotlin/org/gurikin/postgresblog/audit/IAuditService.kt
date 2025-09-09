package org.gurikin.postgresblog.audit

import reactor.core.publisher.Mono

fun interface IAuditService<T> {
    fun audit(sourceId: Long): Mono<T>
}