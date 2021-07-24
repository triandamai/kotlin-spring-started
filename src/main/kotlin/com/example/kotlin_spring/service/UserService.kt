package com.example.kotlin_spring.service



import com.example.kotlin_spring.database.UsersEntity
import com.example.kotlin_spring.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Created by triandamai on 24/07/2021
 *
 **/
@Service
class UserService {
    /**
     * inject repo
     * **/
    @Autowired
    lateinit var userRepository: UserRepository

    /**
     * get all users
     * **/
    fun getAllUsers(): ResponseEntity<Any?> {
        val users = userRepository.findAll();
        return if(users.count() > 1){
            ResponseEntity(users, HttpStatus.OK)
        }else{
            ResponseEntity(users, HttpStatus.NOT_FOUND)
        }
    }

    /**
     * get user by id
     * @param idUser
     * **/
    fun getUserById(idUser: Long): ResponseEntity<Any?> =
        if(userRepository.existsById(idUser)){
            ResponseEntity(userRepository.findById(idUser), HttpStatus.OK)
        }else ResponseEntity(userRepository.findById(idUser), HttpStatus.NOT_FOUND)

    /**
     * save user
     * **/
    fun addNewUser(usersEntity: UsersEntity): ResponseEntity<Any?> =
        ResponseEntity(userRepository.save(usersEntity), HttpStatus.OK)

    /**
     * update user
     * **/
    fun editUser(id:Long,usersEntity: UsersEntity):ResponseEntity<Any?> {
        return if(userRepository.existsById(id)){
            ResponseEntity(userRepository.save(
                usersEntity
            ),HttpStatus.OK)
        }else  ResponseEntity("not found",HttpStatus.NOT_FOUND)
    }
/**
 *
 * **/
    fun deleteUser(id:Long):ResponseEntity<Any?>{
        return if(userRepository.existsById(id)){
            ResponseEntity(userRepository.deleteById(id),HttpStatus.OK)
        }else ResponseEntity("user doesn't exist !!",HttpStatus.NOT_FOUND)
    }
    /**
     *
     * **/

}