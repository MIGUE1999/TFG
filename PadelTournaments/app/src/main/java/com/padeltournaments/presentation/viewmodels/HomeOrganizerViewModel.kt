package com.padeltournaments.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.ITournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeOrganizerViewModel @Inject constructor(
    private val tournamentRepository : ITournamentRepository,
) : ViewModel() {

    val tournamentsByOrganizer : MutableState<List<TournamentEntity>> = mutableStateOf(listOf())

    fun getTournamentByOrgId(orgId:Int) : Flow<List<TournamentEntity>> {
            return tournamentRepository.getTournamentsByOrgId(orgId)
    }


    /*
    fun getTournamentByOrgId(orgId:Int) {
        tournamentRepository.getTournamentsByOrgId(orgId).let{ tournamentsByOrg ->
            tournamentsByOrganizer.value = tournamentsByOrg
        }
    }

    */

    fun deleteTournament(tournament : TournamentEntity){
        viewModelScope.launch(Dispatchers.IO) {
            tournamentRepository.deleteTournament(tournament)
        }
    }
}