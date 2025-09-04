package org.gurikin.postgresblog.users

open class AbstractJpaException(message: String?): RuntimeException(message)
class UnknownJpaException(message: String?): AbstractJpaException(message)

class UserAlreadyExistsException(message: String?): AbstractJpaException(message)