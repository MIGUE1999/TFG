package com.padeltournaments.presentation.viewmodels

import android.util.Log
import android.util.Patterns
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
import com.padeltournaments.util.Rol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository : IUserRepository,
    private val organizerRepository : IOrganizerRepository,
    private val playerRepository: IPlayerRepository
) : ViewModel() {
    val emailUser = mutableStateOf("")
    val passwordUser = mutableStateOf("")

    val validateEmail = mutableStateOf(true)
    val validatePassword = mutableStateOf(true)

    val validateEmailError = "Introduzca un email valido"
    val validatePasswordError = "Introduzca una contraseña valida"

    var isPasswordVisible = mutableStateOf(false)

    private val _userLogged = MutableLiveData<UserEntity>()
    val userLogged: LiveData<UserEntity>
        get() = _userLogged

    fun onEmailChanged(email:String){
        emailUser.value = email
    }
    fun onPasswordChanged(pass:String){
        passwordUser.value = pass
    }
    fun checkLoginCredentials(onCheckUserFinish: (UserEntity) -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                var registeredUsr = userRepository.getUserByCredentials(emailUser.value, passwordUser.value)
                if (registeredUsr != null) {
                    withContext(Dispatchers.Main) {
                        onCheckUserFinish(registeredUsr)
                    }
                }
            }
    }
    fun setSession(user: UserEntity, session: LoginPref){
        viewModelScope.launch(Dispatchers.IO) {
            val rolUsr = user.rol

            if(rolUsr == Rol.organizer) {
                val organizer = organizerRepository.getOrganizerById(user.id)
                if (organizer != null) {
                    Log.d(
                        "LoginViewModel",
                        "Organizator: " + organizer.id + user.name + organizer.clubName
                    )
                    session.createLoginSession(
                        user.id,
                        user.name,
                        user.email,
                        user.rol,
                        organizer.id.toString()
                    )
                }
            }else{
                val play = playerRepository.getPlayerByUserId(user.id)
                if (play != null) {
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
    fun validateData(): Boolean {
        validateEmail.value = Patterns.EMAIL_ADDRESS.matcher(emailUser.value).matches()
        validatePassword.value = passwordUser.value.isNotBlank()

        return validateEmail.value && validatePassword.value
    }
}