package org.gurikin.postgresblog.users

open class AbstractJpaException(message: String?) : RuntimeException(message)
class UnknownJpaException(message: String?) : AbstractJpaException(message)

class UserNotFoundException(
    message: String?,
    val authResultDto: AuthResultDto? = null
) : AbstractJpaException(message)

class UserAlreadyExistsException(
    message: String?,
    val authResultDto: AuthResultDto? = null
) : AbstractJpaException(message)