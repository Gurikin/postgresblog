package org.gurikin.postgresblog.users

data class AuthResultDto(
    val login: String,
    val status: Boolean,
    val message: String? = null,
)
