package com.example.kotlin_spring.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
/**
 * Created by triandamai on 24/07/2021
 *
 **/
@CrossOrigin(maxAge = 3600)
@RestController
class UserController {
    @GetMapping("/",produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun index(): ResponseEntity<Any?>{
        return ResponseEntity("tes",HttpStatus.OK)
    }
}