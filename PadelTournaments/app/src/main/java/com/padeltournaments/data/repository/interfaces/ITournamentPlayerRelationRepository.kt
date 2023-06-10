package com.padeltournaments.data.repository.interfaces

import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.relations.TournamentPlayerRelation
import kotlinx.coroutines.flow.Flow

interface ITournamentPlayerRelationRepository {

    suspend fun insert(tournamentPlayerRelation: TournamentPlayerRelation)

    fun delete(tournamentPlayerRelation: TournamentPlayerRelation)

    fun getTournamentsForPlayer(playerId: Int): List<TournamentEntity>

    fun getPlayersForTournament(tournamentId: Int): List<PlayerEntity>
}