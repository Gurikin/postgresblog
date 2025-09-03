package org.gurikin.postgresblog.users

data class LoginDto(
    val login: String,
    val password: String
)
