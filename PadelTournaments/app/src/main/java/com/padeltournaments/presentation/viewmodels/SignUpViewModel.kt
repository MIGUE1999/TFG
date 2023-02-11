package com.padeltournaments.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.OrganizerEntity
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.data.repository.interfaces.IOrganizerRepository
import com.padeltournaments.data.repository.interfaces.IPlayerRepository
import com.padeltournaments.data.repository.interfaces.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository : IUserRepository,
    private val playerRepository : IPlayerRepository,
    private val organizerRepository : IOrganizerRepository
) : ViewModel() {

    val emailUser = mutableStateOf("")
    val nameUser = mutableStateOf("")
    val surnameUser = mutableStateOf("")
    val passwordUser = mutableStateOf("")
    val tlfUser = mutableStateOf("")
    val nickname = mutableStateOf("")
    val cif = mutableStateOf("")
    val clubName = mutableStateOf("")
    val bankAccount = mutableStateOf("")
    val rol = mutableStateOf("")

    private var idUsr = -1


    fun insertPlayerByUser(user: UserEntity){
            insertUser(user)
            insertPlayerByMail(user.email)
    }

    private fun insertUser(user : UserEntity){
        viewModelScope.launch{
            userRepository.insertUser(user)
        }
    }

    private fun insertPlayerByMail(mail:String){
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            idUsr = userRepository.getIdByMail(mail)
            if(idUsr != -1) {
                val player = PlayerEntity(nickname = nickname.value, userId = idUsr)
                Log.d("SignUpViewM" , player.toString())
                insertPlayer(player)
            }
            idUsr = -1
        }
    }

     private fun insertPlayer(player : PlayerEntity){
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.insertPlayer(player)
        }
    }

    fun insertOrganizerByUser(user : UserEntity){
        viewModelScope.launch(Dispatchers.IO) {
            insertUser(user)
            insertOrganizerByMail(user.email)
        }
    }

    private fun insertOrganizerByMail(mail:String){
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            idUsr = userRepository.getIdByMail(mail)
            val organizer = OrganizerEntity(cif = cif.value ,
                clubName= clubName.value,
                bankAccount= bankAccount.value,
                userId = idUsr )
            insertOrganizer(organizer)
        }
    }

    private fun insertOrganizer(organizer : OrganizerEntity){
        viewModelScope.launch(Dispatchers.IO) {
            organizerRepository.insertOrganizer(organizer)
        }
    }

    fun onEmailChanged(email:String){
        emailUser.value = email
    }
    fun onNameChanged(name:String){
        nameUser.value = name
    }
    fun onSurnameChanged(surname:String){
        surnameUser.value = surname
    }

    fun onPasswordChanged(pass:String){
        passwordUser.value = pass
    }

    fun onTlfChanged(tlf:String){
        tlfUser.value = tlf
    }
    fun onNicknameChanged(nick:String){
        nickname.value = nick
    }

    fun onCifChanged(cf:String){
        cif.value = cf
    }

    fun onClubNameChanged(cn:String){
        clubName.value = cn
    }

    fun onBankAccountChanged(bankAcc:String){
        bankAccount.value = bankAcc
    }

}