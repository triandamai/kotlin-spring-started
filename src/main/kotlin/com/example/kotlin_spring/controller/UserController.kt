package com.example.kotlin_spring.controller


import com.example.kotlin_spring.database.UsersEntity
import com.example.kotlin_spring.repository.UserRepository
import com.example.kotlin_spring.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Created by triandamai on 24/07/2021
 *
 **/
@CrossOrigin(maxAge = 3600)
@RestController
class UserController {
    @Autowired
    lateinit var userService:UserService

    @GetMapping("/user")
    fun index(): ResponseEntity<Any?> = userService.getAllUsers()

    @GetMapping("/user/{id}")
    fun findById(@PathVariable id:Long):ResponseEntity<Any?> = userService.getUserById(id)

    @PostMapping("/user/add")
    fun addUser(@Valid @RequestBody user: UsersEntity):ResponseEntity<Any?> = userService.addNewUser(user)

    @PutMapping("/user/{id}")
    fun updateuser(@PathVariable("id") idUser:Long,@RequestBody user: UsersEntity):ResponseEntity<Any?> = userService.editUser(idUser,user)

    @DeleteMapping("/user/{id}")
    fun deleteUser(@PathVariable("id") idUser:Long):ResponseEntity<Any?> = userService.deleteUser(idUser)

}