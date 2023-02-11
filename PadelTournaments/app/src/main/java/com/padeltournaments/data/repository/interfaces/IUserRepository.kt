package com.padeltournaments.data.repository.interfaces

import androidx.lifecycle.LiveData
import com.padeltournaments.data.entities.UserEntity

interface IUserRepository {

    fun getUser(usrId: Int): LiveData<UserEntity>

    fun getAllUsers(): LiveData<List<UserEntity>>

    fun getIdByMail(mail: String) : Int

    suspend fun getUserByCredentials(mail: String, pass: String) : UserEntity

    suspend fun insertUser(usr: UserEntity)

    suspend fun deleteUser(usr: UserEntity)

    suspend fun modifyUser(usr: UserEntity)
}