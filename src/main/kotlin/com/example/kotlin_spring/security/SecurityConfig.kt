package com.example.kotlin_spring.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import reactor.core.publisher.Mono

@EnableWebFluxSecurity
class SecurityConfig{
    @Bean
    fun passwordEncoder():PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailService(encoder: PasswordEncoder):MapReactiveUserDetailsService{
        val user = User.builder()
            .username("batman")
            .password(encoder.encode("password"))
            .roles("USER")
            .build()
        return MapReactiveUserDetailsService(user)
    }
    @Bean
    fun springSecurityFilterChain(
        http:ServerHttpSecurity,
        authenticationManager: JwtAuthenticationManager,
        converter: ServerAuthenticationConverter
    ):SecurityWebFilterChain{
        val filter = AuthenticationWebFilter(authenticationManager)
        filter.setServerAuthenticationConverter(converter)
        http
            .exceptionHandling()
                //ganti header authorization ke Bearer jika tidak ada token
            .authenticationEntryPoint{
                exchange,_->
                Mono.fromRunnable {
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.headers.set(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
                }
            }
            .and()
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST,"/login").permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(filter,SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
        return http.build()
    }
}