package com.padeltournaments.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.padeltournaments.data.entities.UserEntity
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers() : LiveData<List<UserEntity>>
    @Query("SELECT * FROM user WHERE id = :idUser")
    fun getUserById(idUser: Int) : LiveData<UserEntity>
    @Query("SELECT * FROM user WHERE id = :idUser")
    fun getUserNoLiveById(idUser: Int) : UserEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)
    @Update
    suspend fun updateUser(userEntity: UserEntity)
    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
    @Query("SELECT id FROM user WHERE email = :mail")
    fun getIdByMail(mail: String) : Int
    @Query("SELECT * FROM user WHERE email = :mail AND password = :pass")
    suspend fun getUserByCredentials(mail: String, pass: String) : UserEntity
}