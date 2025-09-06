package org.gurikin.postgresblog.posts

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.apache.commons.lang3.builder.ToStringExclude
import org.gurikin.postgresblog.users.Users
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
data class Posts(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    val postId: Long?,

    @ToStringExclude
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    val user: Users,

    @Column(name = "create_dttm")
    val createDttm: LocalDateTime,

    @Column(name = "title")
    val title: String,

    @Column(name = "content")
    val contest: String
)