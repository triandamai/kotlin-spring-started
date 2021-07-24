package com.example.kotlin_spring.repository


import com.example.kotlin_spring.database.UsersEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Created by triandamai on 24/07/2021
 *
 **/

@Repository
interface UserRepository:CrudRepository<UsersEntity,Long>{
    fun findByLastName(lastName:String):Iterable<UsersEntity>

    @Query("")
    fun findByEmail(userName:String):Optional<UsersEntity>?
}