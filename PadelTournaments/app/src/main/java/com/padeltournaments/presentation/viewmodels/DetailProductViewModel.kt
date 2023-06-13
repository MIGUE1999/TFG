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
import kotlinx.coroutines.flow.*
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

    private val _tournamentId = MutableStateFlow(-1)
    val tournamentId: StateFlow<Int> = _tournamentId

    val usersInscriptedTournament: Flow<List<UserEntity>> = tournamentId.flatMapLatest { id ->
        if (id != 0) {
            tournamentPlayerRelationRepository.getPlayersForTournament(id)
        } else {
            flowOf(emptyList())
        }
    }

    fun setTournamentId(id: Int) {
        _tournamentId.value = id
    }

    // Utilizamos MutableStateFlow para almacenar el valor actualizado
    private val _isUserInTournament = MutableStateFlow(false)
    val isUserInTournament: StateFlow<Boolean> = _isUserInTournament.asStateFlow()

    fun checkPlayerInTournament(idUser: Int, idTournament: Int) {
        viewModelScope.launch {
            tournamentPlayerRelationRepository.isUserInTournament(idUser, idTournament).collect {
                _isUserInTournament.value = it
            }
        }
    }


}