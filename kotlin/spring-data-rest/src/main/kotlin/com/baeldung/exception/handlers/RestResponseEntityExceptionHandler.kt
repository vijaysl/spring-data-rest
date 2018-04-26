package com.baeldung.exception.handlers

import com.baeldung.exception.MyResourceNotFoundException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.data.rest.core.RepositoryConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    // API

    // 400

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleBadRequest(ex: ConstraintViolationException, request: WebRequest): ResponseEntity<Any> {
        logger.error( ex)
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleBadRequest(ex: DataIntegrityViolationException, request: WebRequest): ResponseEntity<Any> {
        logger.error( ex)
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error( ex)
        val bodyOfResponse = "This should be application specific"
        // ex.getCause() instanceof JsonMappingException, JsonParseException
        // for additional information later on
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error( ex)
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request)
    }

    // 404

    @ExceptionHandler(value = [EntityNotFoundException::class, MyResourceNotFoundException::class])
    protected fun handleNotFound(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        logger.error( ex)
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    // 409

    @ExceptionHandler(InvalidDataAccessApiUsageException::class, DataAccessException::class)
    protected fun handleConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        logger.error( ex)
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.CONFLICT, request)
    }

    // 412

    // 500

    @ExceptionHandler(NullPointerException::class, IllegalStateException::class)
        /*500*/ fun handleInternal(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        logger.error("500 Status Code", ex)
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(ex, bodyOfResponse, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request)
    }

    @ExceptionHandler(IllegalArgumentException::class)
        /*422*/ fun handleIllegalArgument(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        logger.error("422 Status Code", ex)
        val errorMap = HashMap<String, String?>()
        errorMap.put("error", "IllegalArgument")
        errorMap.put("message", ex.message)

        val responseHeaders = HttpHeaders()
        responseHeaders.contentType = MediaType.APPLICATION_JSON
        return ResponseEntity(errorMap, HttpHeaders(),
            HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(RepositoryConstraintViolationException::class)
    fun handleAccessDeniedException(
        ex: Exception, request: WebRequest): ResponseEntity<Any> {
        logger.error( ex)
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
        return ResponseEntity(errorMap, HttpHeaders(),
            HttpStatus.PARTIAL_CONTENT)
    }
}
