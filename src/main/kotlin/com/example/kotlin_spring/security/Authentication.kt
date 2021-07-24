package com.example.kotlin_spring.security

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

@Component
class JwtServerAuthentication:ServerAuthenticationConverter{
    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {

        //cek header dari request apakah ada tokennya?
        return Mono.justOrEmpty(exchange?.request?.headers?.getFirst(HttpHeaders.AUTHORIZATION))
            .filter{it.startsWith("Bearer")}
            .map { it.substring(7) }
            .map { jwt-> BearerToken(jwt) }

    }
}


@Component
class JwtAuthenticationManager(
    private val jwtSupport: JwtSupport,
    private val users:ReactiveUserDetailsService
    ):ReactiveAuthenticationManager{

    //validasi token
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {

        return Mono.justOrEmpty(authentication)
            .filter{ auth-> auth is BearerToken}
            .cast(BearerToken::class.java)
            .flatMap { jwt-> mono { validate(jwt) } }
            .onErrorMap { error-> BearerInvalid(error.message)}
    }

    private suspend fun validate(token:BearerToken):Authentication{
        val username = jwtSupport.getUsername(token)
        val user =users.findByUsername(username).awaitSingleOrNull()

        if(jwtSupport.isValid(token,user!!)){
            return UsernamePasswordAuthenticationToken(user!!.username,user!!.password,user!!.authorities)
        }
        throw IllegalArgumentException("token is not valid")
    }

}
class BearerInvalid( message:String?): AuthenticationException(message)