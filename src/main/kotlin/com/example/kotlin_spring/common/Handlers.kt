package com.example.kotlin_spring.common

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.http.HttpMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.function.Consumer
/**
 * Created by triandamai on 24/07/2021
 *
 **/

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(
    MethodArgumentNotValidException::class
)
fun handleValidationExceptions(
    ex: MethodArgumentNotValidException
): Map<String, String?>? {
    val errors: MutableMap<String, String?> = HashMap()
    ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
        val fieldName = (error as FieldError).field
        val errorMessage = error.getDefaultMessage()
        errors[fieldName] = errorMessage
    })
    return errors
}

