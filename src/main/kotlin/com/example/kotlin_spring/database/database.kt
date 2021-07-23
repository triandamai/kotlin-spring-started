package com.example.kotlin_spring.database

import java.lang.reflect.Constructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by triandamai on 24/07/2021
 *
 **/

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val idUser:Long=-1,
    val firstName:String,
    val lastName:String,
){
constructor() : this(0,"","")
}