package org.gurikin.postgresblog.posts

import org.gurikin.postgresblog.users.UserNotFoundException
import org.gurikin.postgresblog.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val postEntityTemplate: R2dbcEntityTemplate
) {
    companion object {
        private val log = LoggerFactory.getLogger(PostService::class.java)
    }

    @Transactional(readOnly = true)
    fun findAllByUser(userId: Long): Mono<MutableList<PostResponseDto?>> {
        // val list = mutableListOf<PostResponseDto?>()
        // return postEntityTemplate.select(Posts::class.java)
        //     .from("posts")
        //     .matching(Query.query(where("user_id").`is`(userId)))
        //     .all()
        val list = mutableListOf<PostResponseDto?>()
        return postEntityTemplate.select(Query.query(where("user_id").`is`(userId)), Posts::class.java)
            .all { list.add(it.toPostResponseDto()) }
            .map { list }

        // val list = mutableListOf<PostResponseDto?>()
        // return postRepository.findAllByUserId(userId)
        //     .map { post ->
        //         log.info("Mapping of posts: {} to PostResponseDto", post)
        //         list.add(post?.toPostResponseDto())
        //     }
        //     .map { list }
        //     .onErrorResume { error ->
        //         log.error(error.message, error)
        //         Mono.error(error)
        //     }
    }

    @Transactional
    fun createPost(postRequestDto: PostRequestDto): Mono<PostResponseDto> {
        return when (postRequestDto.authorId == null) {
            true -> savePostWithoutAuthor(postRequestDto)
            else -> savePostWithAuthor(postRequestDto)
        }
    }

    private fun savePostWithoutAuthor(postRequestDto: PostRequestDto): Mono<PostResponseDto> {
        return postRepository.save(
            Posts(
                postId = null,
                title = postRequestDto.title,
                content = postRequestDto.content,
                createDttm = LocalDateTime.now(),
                userId = null
            )
        ).map { post ->
            log.info("Try to save post without author: {}", postRequestDto)
            post.toPostResponseDto()
        }.onErrorResume { error ->
            log.error("Error saving post", error)
            Mono.error(error)
        }
    }

    private fun savePostWithAuthor(postRequestDto: PostRequestDto): Mono<PostResponseDto> {
        return userRepository.findByUserId(postRequestDto.authorId!!)
            .flatMap { userEntity ->
                log.info("Try to save post with author: {}", postRequestDto)
                postRepository.save(
                    Posts(
                        postId = null,
                        title = postRequestDto.title,
                        content = postRequestDto.content,
                        createDttm = LocalDateTime.now(),
                        userId = postRequestDto.authorId
                    )
                ).map {
                    it.toPostResponseDto()
                }
            }.switchIfEmpty(Mono.error(UserNotFoundException("User with id = ${postRequestDto.authorId} not found")))
            .onErrorResume { error ->
                log.error("Error saving post", error)
                Mono.error(error)
            }
    }
}
