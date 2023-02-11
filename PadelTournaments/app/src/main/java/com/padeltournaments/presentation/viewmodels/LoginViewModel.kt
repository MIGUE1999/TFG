package com.padeltournaments.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.data.repository.interfaces.IOrganizerRepository
import com.padeltournaments.data.repository.interfaces.IPlayerRepository
import com.padeltournaments.data.repository.interfaces.IUserRepository
import com.padeltournaments.util.LoginPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository : IUserRepository,
    private val organizerRepository : IOrganizerRepository,
    private val playerRepository: IPlayerRepository
) : ViewModel() {

    val emailUser = mutableStateOf("")
    val passwordUser = mutableStateOf("")
    private val _userLogged = MutableLiveData<UserEntity>()
    val userLogged: LiveData<UserEntity>
        get() = _userLogged

    fun onEmailChanged(email:String){
        emailUser.value = email
    }

    fun onPasswordChanged(pass:String){
        passwordUser.value = pass
    }

    fun checkLoginCredentials() {
        viewModelScope.launch(Dispatchers.IO) {
            var registeredUsr : UserEntity?
            registeredUsr = userRepository.getUserByCredentials(emailUser.value, passwordUser.value)
            if(registeredUsr != null) {
                _userLogged.postValue(registeredUsr!!)
            }
        }
    }

    fun setSession(user: UserEntity, session: LoginPref){
        viewModelScope.launch(Dispatchers.IO) {
            val organizer = organizerRepository.getOrganizerById(user.id)
            if(organizer != null) {
                Log.d("LoginViewModel", "Organizator: " + organizer.id + user.name + organizer.clubName)
                session.createLoginSession(
                    user.id,
                    user.name,
                    user.email,
                    user.rol,
                    organizer.id.toString()
                )
            }else{
                val play = playerRepository.getPlayerByUserId(user.id)
                if(play != null) {
                    Log.d("LoginViewModel", "Player: " + play.nickname)
                    session.createLoginSession(
                        user.id,
                        user.name,
                        user.email,
                        user.rol,
                        play.id.toString()
                    )
                }
            }
        }
    }
}