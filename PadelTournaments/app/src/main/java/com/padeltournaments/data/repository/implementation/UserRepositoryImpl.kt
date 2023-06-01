package com.padeltournaments.data.repository.implementation

import androidx.lifecycle.LiveData
import com.padeltournaments.data.dao.UserDao
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.data.repository.interfaces.IUserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao : UserDao) : IUserRepository {
    override fun getUser(usrId: Int): LiveData<UserEntity> {
        return userDao.getUserById(usrId)
    }
    override fun getUserNoLiveById(idUser: Int) : UserEntity{
        return userDao.getUserNoLiveById(idUser)
    }
    override fun getAllUsers(): LiveData<List<UserEntity>> {
        return userDao.getAllUsers()
    }
    override fun getIdByMail(mail: String) = userDao.getIdByMail(mail)
    override suspend fun getUserByCredentials(mail: String, pass: String): UserEntity {
        return userDao.getUserByCredentials(mail, pass)
    }
    override suspend fun insertUser(usr: UserEntity){
        return userDao.insertUser(usr)
    }
    override suspend fun deleteUser(usr: UserEntity) {
        return userDao.deleteUser(usr)
    }
    override suspend fun modifyUser(usr: UserEntity) {
        return userDao.updateUser(usr)
    }
}