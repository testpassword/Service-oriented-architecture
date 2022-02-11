package service1.configs

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import service1.FilterError
import service1.PaginationError
import service1.SortersError
import javax.servlet.http.HttpServletRequest

@ControllerAdvice class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class) fun handle(req: HttpServletRequest, e: Exception): ResponseEntity<String> =
        when(e) {
            is MissingServletRequestParameterException, is FilterError, is PaginationError, is SortersError, is HttpRequestMethodNotSupportedException ->
                e.localizedMessage to HttpStatus.BAD_REQUEST
            is EmptyResultDataAccessException, is NoSuchElementException ->
                "entity with requested id didn't exist" to HttpStatus.NOT_FOUND
            is HttpMessageNotReadableException, is MethodArgumentTypeMismatchException ->
                "request body or params does not match required pattern, check API documentation" to HttpStatus.BAD_REQUEST
            else -> {
                e.printStackTrace()
                "unexpected exception, contact support" to HttpStatus.INTERNAL_SERVER_ERROR
            }
        }.let { ResponseEntity(it.first, it.second) }
}