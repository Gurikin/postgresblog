package org.gurikin.postgresblog.posts

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/posts")
class PostsController(
    private val postService: PostService,
) {
    @GetMapping()
    fun getAllPostsByUserId(@RequestParam("searchText") searchText: List<String>): Mono<ResponseEntity<List<PostResponseDto?>>> {
        return postService.findAllByFullTextSearch(searchText)
            .flatMap { posts -> Mono.just(ResponseEntity.ok(posts)) }
    }

    @GetMapping("/{userId}")
    fun getAllPostsByUserId(@PathVariable("userId") userId: Long): Mono<ResponseEntity<MutableList<PostResponseDto?>>> {
        return postService.findAllByUser(userId)
            .flatMap { posts -> Mono.just(ResponseEntity.ok(posts)) }
    }

    @PostMapping
    fun login(@RequestBody postRequestDto: PostRequestDto): Mono<ResponseEntity<PostResponseDto>> {
        return postService.createPost(postRequestDto)
            .flatMap { post -> Mono.just(ResponseEntity.ok(post)) }
    }
}