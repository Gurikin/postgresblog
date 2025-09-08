package org.gurikin.postgresblog.audit

import jakarta.persistence.Column
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnTransformer
import java.time.LocalDateTime

@Table(name = "audit_log")
data class AuditLog(
    @Column(name = "operation")
    val operation: OperationType,
    @Column(name = "create_dttm")
    val createDttm: LocalDateTime,
    @Column(name = "table_name")
    val tableName: String,
    @ColumnTransformer(write = "?::jsonb")
    @Column(name = "old_value")
    val oldValue: String,
    @ColumnTransformer(write = "?::jsonb")
    @Column(name = "new_value")
    val newValue: String
)