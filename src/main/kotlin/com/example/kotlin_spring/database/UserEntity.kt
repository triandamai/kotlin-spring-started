package com.example.kotlin_spring.database

import org.springframework.lang.Nullable
import java.lang.reflect.Constructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.Min

/**
 * Created by triandamai on 24/07/2021
 *
 **/

@Entity(name = "users")
data class UsersEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Nullable
    val idUser:Long?,
    val firstName:String,
    val lastName:String,
){
constructor() : this(0,"","")
constructor(firstName: String,lastName: String) : this(idUser = null,firstName=firstName,lastName=lastName)
}