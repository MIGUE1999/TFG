package com.padeltournaments.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.IOrganizerRepository
import com.padeltournaments.data.repository.interfaces.IPlayerRepository
import com.padeltournaments.data.repository.interfaces.ITournamentPlayerRelationRepository
import com.padeltournaments.data.repository.interfaces.ITournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeOrganizerViewModel @Inject constructor(
    private val tournamentRepository : ITournamentRepository,
    private val tournamentPlayerRelationRepository: ITournamentPlayerRelationRepository
) : ViewModel() {

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId

    var isTournamentList = mutableStateOf(true)

    val tournamentsByUserId: Flow<List<TournamentEntity>> = userId.flatMapLatest { id ->
        if (id != 0) {
            tournamentRepository.getTournamentsByUserId(id)
        } else {
            flowOf(emptyList())
        }
    }

    val tournamentsPlayerByUserId: Flow<List<TournamentEntity>> = userId.flatMapLatest { id ->
        if (id != 0) {
            tournamentPlayerRelationRepository.getTournamentsByUserId(id)
        } else {
            flowOf(emptyList())
        }
    }

    fun setUserId(id: Int) {
        _userId.value = id
    }

    fun deleteTournament(tournament : TournamentEntity, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tournamentRepository.deleteTournament(tournament)
        }
    }
    /*
    fun getOrganizerTournamentsByUserId(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val organizer = organizerRepository.getOrganizerByUserId(userId)
            val tournaments = tournamentRepository.getTournamentsByOrgId(organizer.id)
            _tournamentsFlow.value = tournaments
        }
    }
    fun getPlayerTournamentsByUserId(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val player = playerRepository.getPlayerByUserId(userId)
            player?.id?.let { playerId ->
                val tournaments = tournamentPlayerRelationRepository.getTournamentsForPlayer(playerId)
                _tournamentsFlow.value = tournaments
            }
        }
    }
*/
}