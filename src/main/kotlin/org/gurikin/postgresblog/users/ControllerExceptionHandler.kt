package org.gurikin.postgresblog.users

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseBody
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<AuthResultDto?> {
        val authResultDto = ex.authResultDto
        return ResponseEntity(authResultDto, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseBody
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<Any> {
        val authResultDto = ex.authResultDto
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResultDto)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleGenericException(ex: Exception): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
    }
}