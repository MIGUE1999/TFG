package com.padeltournaments.presentation.viewmodels

import android.graphics.Bitmap
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.OrganizerEntity
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.data.repository.interfaces.IOrganizerRepository
import com.padeltournaments.data.repository.interfaces.IPlayerRepository
import com.padeltournaments.data.repository.interfaces.IUserRepository
import com.padeltournaments.util.Rol
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

    val nameUser = mutableStateOf("")
    val emailUser = mutableStateOf("")
    val surnameUser = mutableStateOf("")
    val passwordUser = mutableStateOf("")
    val confirmPasswordUser = mutableStateOf("")
    val tlfUser = mutableStateOf("")
    val nickname = mutableStateOf("")
    val cif = mutableStateOf("")
    val clubName = mutableStateOf("")
    val bankAccount = mutableStateOf("")
    val rol = mutableStateOf(Rol.player)
    val photo = mutableStateOf<Bitmap?>(null)
    val isPressed = mutableStateOf(false)

    val showForm = mutableStateOf(false)

    val validateName = mutableStateOf(true)
    val validateSurname = mutableStateOf(true)
    val validateEmail = mutableStateOf(true)
    val validatePhone = mutableStateOf(true)
    val validatePassword = mutableStateOf(true)
    val validatePasswordsEqual = mutableStateOf(true)
    val validateNickname = mutableStateOf(true)
    val validateCif = mutableStateOf(true)
    val validateClubName = mutableStateOf(true)
    val validateBankAccount = mutableStateOf(true)
    val validatePhoto = mutableStateOf(true)

    val validateNameError = "Introduzca un nombre valido"
    val validateSurnameError = "Introduzca un apellido valido"
    val validateEmailError = "Introduzca un email valido"
    val validatePhoneError = "Introduzca un numero de telefono valido"
    val validatePasswordError = "Introduzca una contraseña valida"
    val validateEqualPassowrdError = "Las contraseñas deben coincidir"
    val validateNicknameError = "Introduzca un nickname valido"
    val validateCifError = "Introduzca un cif valido. Ej: A333564109"
    val validateClubNameError = "Introduzca un nombre de club valido"
    val validateBankAccountError = "Introduzca una cuenta bancaria valida. Ej: ES2114650100722030876293"
    val validatePhotorError = "Introduzca foto de perfil"

    var isPasswordVisible = mutableStateOf(false)
    var isConfirmPasswordVisible = mutableStateOf(false)

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
    fun onConfirmPasswordChanged(pass:String){
        confirmPasswordUser.value = pass
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

    private fun isCif(): Boolean {
        val firstChar = cif.value.firstOrNull() ?: return false

        if( cif.value.length == 10 && firstChar.isLetter()){
            return true
        }
        return false
    }

    private fun isIban(): Boolean {
        val firtsTwoChars = bankAccount.value.take(2)
        if( firtsTwoChars.length == 2 && firtsTwoChars.all { it.isLetter() })
            return true
        return false
    }
    fun validateData(): Boolean{
        validateName.value = nameUser.value.isNotBlank()
        validateSurname.value = surnameUser.value.isNotBlank()
        validateEmail.value = Patterns.EMAIL_ADDRESS.matcher(emailUser.value).matches()
        validatePhone.value = Patterns.PHONE.matcher(tlfUser.value).matches()
        validatePassword.value = passwordUser.value.isNotBlank()
        validatePasswordsEqual.value = passwordUser.value == confirmPasswordUser.value
        validatePhoto.value = photo.value != null

        if(rol.value == Rol.player){
            validateNickname.value = nickname.value.isNotBlank()
            return validateName.value && validateSurname.value &&
                    validateEmail.value && validatePhone.value
                    && validatePassword.value && validatePasswordsEqual.value && validatePhoto.value && validateNickname.value
        }
        else {
            validateCif.value = cif.value.isNotBlank() && isCif()
            validateBankAccount.value = bankAccount.value.isNotBlank() && isIban()
            validateClubName.value = clubName.value.isNotBlank()
            return validateName.value && validateSurname.value
                    && validateEmail.value && validatePhone.value
                    && validatePassword.value && validatePasswordsEqual.value
                    && validateCif.value && validateBankAccount.value && validatePhoto.value
                    && validateClubName.value
        }
    }
    fun updateOrganizer(idUser: String){
        val user = UserEntity(id= idUser.toInt(), name = nameUser.value, password = passwordUser.value,
        email = emailUser.value, telephoneNumber = tlfUser.value, surname = surnameUser.value, rol = Rol.organizer, photo = photo.value)
        viewModelScope.launch(Dispatchers.IO) {
            val organizer = getOrganizerByUserId(idUser)
            val newOrganizer = OrganizerEntity(id = organizer.id, cif = cif.value,
                clubName = clubName.value, bankAccount = bankAccount.value, userId = idUser.toInt())
            userRepository.modifyUser(user)
            organizerRepository.updateOrganizer(newOrganizer)
        }
    }
    fun updatePlayer(idUser: String){
        val user = UserEntity(id= idUser.toInt(), name = nameUser.value, password = passwordUser.value,
            email = emailUser.value, telephoneNumber = tlfUser.value, surname = surnameUser.value, rol = Rol.player, photo = photo.value)
        viewModelScope.launch(Dispatchers.IO) {
            val player = getPlayerByUserId(idUser)
            player?.let {
                val newPlayer = PlayerEntity(id = it.id, nickname = nickname.value, userId = idUser.toInt())
                userRepository.modifyUser(user)
                playerRepository.updatePlayer(newPlayer) }
        }
    }
    fun refreshTextFieldValues(idUser: String, isOrganizer: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            val userData = getUser(idUser)
            nameUser.value = userData.name
            emailUser.value = userData.email
            surnameUser.value = userData.surname
            passwordUser.value = userData.password
            confirmPasswordUser.value = userData.password
            tlfUser.value = userData.telephoneNumber
            photo.value = userData.photo
            if (isOrganizer) {
                val organizer = getOrganizerByUserId(idUser)
                rol.value = Rol.organizer
                cif.value = organizer.cif
                clubName.value = organizer.clubName
                bankAccount.value = organizer.bankAccount
            } else {
                val player = getPlayerByUserId(idUser)
                rol.value = Rol.player
                player?.let {
                    nickname.value = it.nickname
                }
            }
        }
    }
    private fun getUser(idUser: String): UserEntity{
        return userRepository.getUserNoLiveById(idUser.toInt())
    }
    private fun getOrganizerByUserId(idUser: String): OrganizerEntity{
        return organizerRepository.getOrganizerByUserId(idUser.toInt())
    }
    private fun getPlayerByUserId(idUser: String): PlayerEntity?{
        return playerRepository.getPlayerByUserId(idUser.toInt())
    }

    fun onPhotoChanged(img: Bitmap){
        this.photo.value = img
    }

}