package com.padeltournaments.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.IPlayerRepository
import com.padeltournaments.data.repository.interfaces.ITournamentPlayerRelationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val playerRepository : IPlayerRepository,
    private val tournamentPlayerRelationRepository: ITournamentPlayerRelationRepository
) : ViewModel() {

    private val _tournamentsFlow: MutableStateFlow<List<TournamentEntity>> = MutableStateFlow(emptyList())
    val tournamentsFlow: StateFlow<List<TournamentEntity>> = _tournamentsFlow

    fun insertPlayerTournamentRelationByUserId(idUser: String, tournamentViewModel: CreateTournamentViewModel, idTournament: String){
        viewModelScope.launch(Dispatchers.IO) {
            val idPlayer = getPlayerByUserId(idUser)?.id
            if (idPlayer != null) {
                tournamentViewModel.insertPlayerTournamentRelation(idTournament.toInt(), idPlayer)
            }
        }
    }
    private fun getPlayerByUserId(idUser: String): PlayerEntity?{
        return playerRepository.getPlayerByUserId(idUser.toInt())
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
}