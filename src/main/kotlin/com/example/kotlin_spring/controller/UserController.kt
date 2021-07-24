package com.example.kotlin_spring.controller



import com.example.kotlin_spring.security.JwtSupport
import com.example.kotlin_spring.service.UserService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder


import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.nio.file.attribute.UserPrincipal
import java.security.Principal


/**
 * Created by triandamai on 24/07/2021
 *
 **/
@CrossOrigin(maxAge = 3600)
@RestController
class UserController(
    private val encoder: PasswordEncoder,
    private val users:ReactiveUserDetailsService,
    private val jwtSupport: JwtSupport
    ) {
    @Autowired
    lateinit var userService:UserService

    @PostMapping("/login")
    suspend fun login(@RequestBody login:Login):Any{
        val user = users.findByUsername(login.username).awaitSingleOrNull()

        user?.let {
            if(encoder.matches(login.password,it.password)){
                return jwtSupport.generate(it.username).value
            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/hai")
    suspend fun hai(@AuthenticationPrincipal principal: Principal):Principal{
        return principal
    }
}

data class Login(val username:String,val password:String)