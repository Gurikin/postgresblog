package org.gurikin.postgresblog.posts

data class PostRequestDto(
    val title: String,
    val content: String,
    val authorId: Long? = null,
)
