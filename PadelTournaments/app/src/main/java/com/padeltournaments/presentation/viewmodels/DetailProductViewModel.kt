package com.padeltournaments.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.data.repository.interfaces.IPlayerRepository
import com.padeltournaments.data.repository.interfaces.ITournamentPlayerRelationRepository
import com.padeltournaments.data.repository.interfaces.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val playerRepository : IPlayerRepository,
    private val tournamentPlayerRelationRepository: ITournamentPlayerRelationRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _tournamentsFlow: MutableStateFlow<List<TournamentEntity>> = MutableStateFlow(emptyList())
    val tournamentsFlow: StateFlow<List<TournamentEntity>> = _tournamentsFlow

    private val _playersFlow: MutableStateFlow<List<UserEntity>> = MutableStateFlow(emptyList())
    val playersFlow: StateFlow<List<UserEntity>> = _playersFlow

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

    fun getTournamentPlayersByTournamentId(tournamentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val players = tournamentPlayerRelationRepository.getPlayersForTournament(tournamentId)
            val users: ArrayList<UserEntity> = ArrayList()
            players.forEach{
                val usuario = userRepository.getUserNoLiveById(it.userId)
                if (usuario != null) {
                    users.add(usuario)
                }
            }
            val usersFinal = users.toList()

            _playersFlow.value = usersFinal
        }
    }

}