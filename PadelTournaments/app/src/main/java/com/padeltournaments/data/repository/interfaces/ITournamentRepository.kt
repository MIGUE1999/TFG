package com.padeltournaments.data.repository.interfaces

import androidx.lifecycle.LiveData
import com.padeltournaments.data.entities.TournamentEntity
import kotlinx.coroutines.flow.Flow

interface ITournamentRepository {
    fun getAllTournaments() : LiveData<List<TournamentEntity>>

    fun getTournamentById(idTournament: Int) : LiveData<TournamentEntity>

    suspend fun insertTournament(tournamentEntity: TournamentEntity)

    suspend fun insertTournaments(tournamentEntities: List<TournamentEntity>)

    suspend fun updateTournament(tournamentEntity: TournamentEntity)

    suspend fun deleteTournament(tournamentEntity: TournamentEntity)

    suspend fun deleteTournaments(tournamentEntities: List<TournamentEntity>)

    fun getTournamentsByOrgId(idOrg : Int) : Flow<List<TournamentEntity>>

}