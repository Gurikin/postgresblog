package org.gurikin.postgresblog.users

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "login")
    val login: String,

    @Column(name = "email")
    val email: String
)