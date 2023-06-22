package com.padeltournaments.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.ICourtRepository
import com.padeltournaments.data.repository.interfaces.IOrganizerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateCourtViewModel @Inject constructor(
    private val courtRepository : ICourtRepository,
    private val organizerRepository: IOrganizerRepository
) : ViewModel() {

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId

    val courtsByUserId: Flow<List<CourtEntity>> = userId.flatMapLatest { id ->
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
    val reservedHours = mutableStateOf(listOf<String>())
    val date = mutableStateOf("")
    val bookCost = mutableStateOf("")
    val province = mutableStateOf("")


    val validateUbication = mutableStateOf(true)
    val validateCourtNumber = mutableStateOf(true)
    val validateDate = mutableStateOf(true)
    val validateBookCost = mutableStateOf(true)
    val validateProvince = mutableStateOf(true)



    val validateUbicationError = "Introduzca una ubicacion valida"
    val validateCourtNumberError = "Introduzca un numero de pista valido"
    val validateDateError = "Introduzca la fecha correcta"
    val validateBookCostError = "Introduzca la fecha correcta"
    val validateProvinceError = "Introduzca la fecha correcta"


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

    fun onBookCostChange(bookCost: String){
        this.bookCost.value = bookCost
    }

    fun validateData(): Boolean {
        validateUbication.value = ubication.value.isNotBlank()
        validateCourtNumber.value = courtNumber.value.isNotBlank()
        validateBookCost.value = validateBookCost.value.toString().isNotBlank()
        validateProvince.value = province.value.isNotBlank()
        if (date.value != "") {
            val format = SimpleDateFormat("dd/MM/yyyy")
            val date = format.parse(date.value)
            if (date != null ) {
                validateDate.value = date >=  Date()
            } else {
                validateDate.value = false
            }
        } else{ validateDate.value = false}


        return validateUbication.value &&
                validateCourtNumber.value &&
                validateBookCost.value && validateProvince.value
    }

    fun getClubNameByUserId(idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val clubName = courtRepository.getClubNameByUserId(idUser)
            clubNameState.value = clubName
        }
    }

    fun getClubNameByOrganizerId(idOrg: Int): Flow<String> {
        return organizerRepository.getClubNameByOrganizerId(idOrg)
    }


    private fun setCourt(court: CourtEntity){
        courtNumber.value = court.courtNumber
        ubication.value = court.ubication
        reservedHours.value = court.reservedHours
        bookCost.value = court.bookCost.toString()
        province.value = court.province
    }

    fun setCourtById(idCourt: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val court = courtRepository.getCourtById(idCourt)
            setCourt(court)
        }
    }

    fun onDateChanged(date : String){
        this.date.value = date
    }

    fun onProvinceChanged(province : String){
        this.province.value = province
    }

}