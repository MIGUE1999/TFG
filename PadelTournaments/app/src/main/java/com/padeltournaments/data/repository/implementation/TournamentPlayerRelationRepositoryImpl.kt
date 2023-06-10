package com.padeltournaments.data.repository.implementation

import com.padeltournaments.data.dao.TournamentPlayerRelationDao
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.relations.TournamentPlayerRelation
import com.padeltournaments.data.repository.interfaces.ITournamentPlayerRelationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TournamentPlayerRelationRepositoryImpl @Inject constructor(private val tournamentPlayerRelationDao: TournamentPlayerRelationDao):
    ITournamentPlayerRelationRepository {
    override suspend fun insert(tournamentPlayerRelation: TournamentPlayerRelation) {
        tournamentPlayerRelationDao.insert(tournamentPlayerRelation)
    }

    override fun delete(tournamentPlayerRelation: TournamentPlayerRelation) {
        tournamentPlayerRelationDao.delete(tournamentPlayerRelation)
    }

    override fun getTournamentsForPlayer(playerId: Int): List<TournamentEntity> {
        return tournamentPlayerRelationDao.getTournamentsForPlayer(playerId)
    }

    override fun getPlayersForTournament(tournamentId: Int): List<PlayerEntity> {
        return tournamentPlayerRelationDao.getPlayersForTournament(tournamentId)
    }
}