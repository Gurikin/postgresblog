package org.gurikin.postgresblog.posts

import java.time.LocalDateTime

data class PostResponseDto(
    val postId: Long?,

    val userId: Long?,

    val createDttm: LocalDateTime,

    val title: String,

    val content: String
)

fun Posts.toPostResponseDto(): PostResponseDto {
    return PostResponseDto(
        postId = this.postId,
        userId = this.userId,
        createDttm = this.createDttm,
        title = this.title,
        content = this.content
    )
}
