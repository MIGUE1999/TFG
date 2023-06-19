package com.padeltournaments.presentation.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.relations.TournamentPlayerRelation
import com.padeltournaments.data.repository.interfaces.ITournamentPlayerRelationRepository
import com.padeltournaments.data.repository.interfaces.ITournamentRepository
import com.padeltournaments.util.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject
@HiltViewModel
class CreateTournamentViewModel @Inject constructor(
    private val tournamentRepository : ITournamentRepository,
    private val tournamentPlayerRelationRepository: ITournamentPlayerRelationRepository
) : ViewModel() {
    val nameTournament = mutableStateOf("")
    val inscriptionCost = mutableStateOf("")
    val prizeTournament = mutableStateOf("")
    val category = mutableStateOf(Category.first)
    val dateIni = mutableStateOf("")
    val dateEnd = mutableStateOf("")
    val poster = mutableStateOf<Bitmap?>(null)
    val enableInscription = mutableStateOf(true)
    val maxNumberInscriptions = mutableStateOf("")
    val ubication = mutableStateOf("")
    val province = mutableStateOf("")



    val validateName = mutableStateOf(true)
    val validateInscriptionCost = mutableStateOf(true)
    val validatePrize = mutableStateOf(true)
    val validateDate = mutableStateOf(true)
    val validatePoster = mutableStateOf(true)
    val validateMaxNumberInscriptions = mutableStateOf(true)
    val validateUbication = mutableStateOf(true)
    val validateProvince = mutableStateOf(true)

    val validateNameError = "Introduzca un nombre valido"
    val validateInscriptionCostError = "Introduzca un precio de inscripcion valido"
    val validatePrizeError = "Introduzca un premio valido"
    val validateDateError = "Introduzca las fechas correctamente"
    val validatePosterError = "Introduzca el cartel del torneo"
    val validateMaxNumberInscriptionsError = "Introduzca un numero maximo de jugadores correcto"
    val validateUbicationError = "Introduzca una ubicacion correcta"
    val validateProvinceError = "Introduzca una provincia"


    fun createTournament(idOrg: Int){
        val newTournament = TournamentEntity(name = nameTournament.value,
            inscriptionCost = inscriptionCost.value.toInt(),
            prize = prizeTournament.value.toInt(),
            category = category.value,
            startDate = dateIni.value,
            endDate = dateEnd.value,
            poster = poster.value,
            maxNumberInscriptions = maxNumberInscriptions.value.toInt(),
            ubication = ubication.value,
            province = province.value,
            idOrganizer = idOrg
        )
        insertTournament(newTournament)
    }
    private fun insertTournament(tournament : TournamentEntity){
        viewModelScope.launch(Dispatchers.IO) {
            tournamentRepository.insertTournament(tournament)
        }
    }
    fun updateTournament(idTournament: Int, idOrg: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val updatedTournament = TournamentEntity(
                id = idTournament,
                name = nameTournament.value,
                inscriptionCost = inscriptionCost.value.toInt(),
                prize = prizeTournament.value.toInt(),
                category = category.value,
                startDate = dateIni.value,
                endDate = dateEnd.value,
                poster = poster.value,
                maxNumberInscriptions = maxNumberInscriptions.value.toInt(),
                ubication = ubication.value,
                province = province.value,
                idOrganizer = idOrg
            )
            tournamentRepository.updateTournament(updatedTournament)
        }
    }
    fun validateData(): Boolean {
        validateName.value = nameTournament.value.isNotBlank()
        validateInscriptionCost.value = inscriptionCost.value.isNotBlank()
        validatePrize.value = prizeTournament.value.isNotBlank()
        validateMaxNumberInscriptions.value = maxNumberInscriptions.value.isNotBlank()
        validateUbication.value = ubication.value.isNotBlank()
        validateProvince.value = province.value.isNotBlank()

        if (dateIni.value != "" && dateEnd.value != "") {
            val format = SimpleDateFormat("dd/MM/yyyy")
            val dateIni = format.parse(dateIni.value)
            val dateEnd = format.parse(dateEnd.value)
            if (dateIni != null && dateEnd != null) {
                validateDate.value = dateIni <= dateEnd
            } else {
                validateDate.value = false
            }
        } else{ validateDate.value = false}

        validatePoster.value = poster.value != null


        return validateName.value && validateInscriptionCost.value && validatePrize.value &&
                validateDate.value && validatePoster.value && validateMaxNumberInscriptions.value
                && validateUbication.value && validateProvince.value
    }
    fun onNameChanged(name:String){
        nameTournament.value = name
    }
    fun onPrizeChanged(price:String){
        prizeTournament.value = price
    }
    fun onInscriptionCostChanged(insCost:String){
        inscriptionCost.value = insCost
    }
    fun onCategoryChanged(category:String){
        this.category.value = category
    }
    fun onDateInitChanged(dateInit : String){
        this.dateIni.value = dateInit
    }
    fun onDateFinChanged(dateFin : String){
        this.dateEnd.value = dateFin
    }
    fun onCartelChanged(img: Bitmap){
        this.poster.value = img
    }
    fun onMaxNumberInscriptionsChanged(maxNumberInscription: String){
        this.maxNumberInscriptions.value = maxNumberInscription
    }
    fun onUbicationChanged(ubi: String){
        this.ubication.value = ubi
    }
    fun onProvinceChanged(province: String){
        this.province.value = province
    }
    private fun setTournament(tournament: TournamentEntity){
        nameTournament.value = tournament.name
        inscriptionCost.value = tournament.inscriptionCost.toString()
        prizeTournament.value = tournament.prize.toString()
        category.value = tournament.category
        dateIni.value = tournament.startDate
        dateEnd.value = tournament.endDate
        poster.value = tournament.poster
        maxNumberInscriptions.value = tournament.maxNumberInscriptions.toString()
        ubication.value = tournament.ubication
        province.value = tournament.province
    }

    fun setTournamentById(idTournament: Int){
        viewModelScope.launch(Dispatchers.IO) {
             val tournament = tournamentRepository.getTournamentByIdStatic(idTournament)
             setTournament(tournament = tournament)
        }
    }
    //MARK: PlayerTournamentRelation operations
    fun insertPlayerTournamentRelation(tournamentId: Int, playerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tournamentPlayerRelation = TournamentPlayerRelation(tournamentId = tournamentId, playerId = playerId)
            tournamentPlayerRelationRepository.insert(tournamentPlayerRelation)
        }
    }

}