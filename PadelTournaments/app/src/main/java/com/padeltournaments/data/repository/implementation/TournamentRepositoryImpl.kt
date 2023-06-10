package com.padeltournaments.data.repository.implementation

import androidx.lifecycle.LiveData
import com.padeltournaments.data.dao.TournamentDao
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.ITournamentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class TournamentRepositoryImpl @Inject constructor(private val tournamentDao:TournamentDao): ITournamentRepository {
    override fun getAllTournaments() = tournamentDao.getAll()
    override fun getAllTournamentsStatic() = tournamentDao.getAllTournamentsStatic()
    override fun getTournamentById(idTournament: Int): LiveData<TournamentEntity> {
        return tournamentDao.getById(idTournament)
    }
    override fun getTournamentByIdStatic(idTournament: Int): TournamentEntity {
        return tournamentDao.getTournamentById(idTournament)
    }
    override suspend fun insertTournament(tournamentEntity: TournamentEntity) {
        tournamentDao.insertTournament(tournamentEntity)
    }
    override suspend fun insertTournaments(tournamentEntities: List<TournamentEntity>) {
        tournamentDao.insertTournaments(tournamentEntities)
    }
    override suspend fun updateTournament(tournamentEntity: TournamentEntity) {
        tournamentDao.updateTournament(tournamentEntity)
    }
    override suspend fun deleteTournament(tournamentEntity: TournamentEntity) {
        tournamentDao.deleteTournament(tournamentEntity)
    }
    override suspend fun deleteTournaments(tournamentEntities: List<TournamentEntity>) {
        tournamentDao.deleteTournaments(tournamentEntities)
    }
    override fun getTournamentsByOrgId(idOrg : Int): List<TournamentEntity> {
        return tournamentDao.getTournamentsByOrgId(idOrg)
    }
}