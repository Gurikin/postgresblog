package org.gurikin.postgresblog.users

data class SignUpDto(
    val login: String,
    val email: String,
    val password: String
)
