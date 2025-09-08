package org.gurikin.postgresblog.audit

import java.time.LocalDateTime

abstract class AuditLogDto<T>(
    open val operation: OperationType,
    open val logTime: LocalDateTime,
    open val tableName: String,
    open val oldValue: T?,
    open val newValue: T?
)