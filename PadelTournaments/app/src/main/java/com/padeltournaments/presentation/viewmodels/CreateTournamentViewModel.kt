package com.padeltournaments.presentation.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.ITournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTournamentViewModel @Inject constructor(
    private val tournamentRepository : ITournamentRepository,
) : ViewModel() {

    private val nameTournament = mutableStateOf("")
    private val prizeTournament = mutableStateOf("")
    private val inscriptionCost = mutableStateOf("")
    private val category = mutableStateOf("")
    private val dateIni = mutableStateOf("")
    private val dateEnd = mutableStateOf("")
    private val dateLimit = mutableStateOf("")
    val poster = mutableStateOf<Bitmap?>(null)

    fun insertTournament(tournament : TournamentEntity){
        viewModelScope.launch(Dispatchers.IO) {
            tournamentRepository.insertTournament(tournament)
        }
    }

    fun getNameTournament() : String{
        return nameTournament.value
    }

    fun getPrize() : String{
        return prizeTournament.value
    }

    fun getInscriptionCost() : String{
        return inscriptionCost.value
    }

    fun getCategory() : MutableState<String>{
        return category
    }

    fun getDateIni() : String{
        return dateIni.value
    }

    fun getDateEnd() : String{
        return dateEnd.value
    }

    fun getDateLimit() : String{
        return dateLimit.value
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

    fun onDateLimitChanged(dateLimit : String){
        this.dateLimit.value = dateLimit
    }

    fun onCartelChanged(img: Bitmap?){
        this.poster.value = img
    }
}