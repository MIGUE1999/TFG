package com.padeltournaments.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.ICourtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class CreateCourtViewModel @Inject constructor(
    private val courtRepository : ICourtRepository
) : ViewModel() {

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId

    val courtsByUserId: Flow<List<TournamentEntity>> = userId.flatMapLatest { id ->
        if (id != 0) {
            courtRepository.getCourtsByUserId(id)
        } else {
            flowOf(emptyList())
        }
    }

    fun setUserId(id: Int) {
        _userId.value = id
    }

    val ubication = mutableStateOf("")
    val courtNumber = mutableStateOf("")
    val organizerId = mutableStateOf("")
    val selectedDate = mutableStateOf("")
    val clubNameState = mutableStateOf("")

    val validateUbication = mutableStateOf(true)
    val validateCourtNumber = mutableStateOf(true)

    val validateUbicationError = "Introduzca una ubicacion valida"
    val validateCourtNumberError = "Introduzca un numero de pista valido"

    fun insertCourt(court: CourtEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            courtRepository.insertCourt(court)
        }
    }

    fun deleteCourt(court: CourtEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            courtRepository.deleteCourt(court)
        }
    }

    fun updateCourt(court: CourtEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            courtRepository.updateCourt(court)
        }
    }

    fun onUbicationChange(newUbi: String){
        ubication.value = newUbi
    }

    fun onCourtNumberChange(newCourtNumber: String){
        courtNumber.value = newCourtNumber
    }

    fun validateData(): Boolean {
        validateUbication.value = ubication.value.isNotBlank()
        validateCourtNumber.value = courtNumber.value.isNotBlank()

        return validateUbication.value && validateCourtNumber.value
    }

    fun getClubNameByUserId(idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val clubName = courtRepository.getClubNameByUserId(idUser)
            clubNameState.value = clubName
        }
    }

}