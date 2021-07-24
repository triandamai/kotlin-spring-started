package com.example.kotlin_spring.database

import org.springframework.lang.Nullable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.lang.reflect.Constructor
import javax.persistence.*


/**
 * Created by triandamai on 24/07/2021
 *
 **/

@Entity(name = "users")
 class UsersEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Nullable
    var idUser:Long? = null
    var firstName:String=""
    var lastName:String=""
    var email:String=""
    var userName:String=""
    var password:String =""
    @Nullable
    var token:String?=""

}