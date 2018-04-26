package com.baeldung.exception.handlers

import org.springframework.data.rest.core.RepositoryConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RepositoryConstraintViolationException::class)
    fun handleAccessDeniedException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val nevEx = ex as RepositoryConstraintViolationException
        val errorMap = HashMap<String, String?>()
        val errors = nevEx.errors.fieldErrors

        for (err: FieldError in errors) {
            val field = err.field
            var code: String? = err.code
            code = code?.replace(field + ".", "")

            errorMap.put(field, code)

        }
        val responseHeaders = HttpHeaders()
        responseHeaders.contentType = MediaType.APPLICATION_JSON
        return ResponseEntity(errorMap, HttpHeaders(), HttpStatus.NOT_ACCEPTABLE)
    }
}
