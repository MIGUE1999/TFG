package com.padeltournaments.data.repository.implementation

import com.padeltournaments.data.dao.CourtPlayerCrossRefDao
import com.padeltournaments.data.entities.relations.CourtPlayerCrossRef
import com.padeltournaments.data.repository.interfaces.ICourtPlayerCrossRefRepository
import kotlinx.coroutines.flow.Flow

class CourtPlayerCrossRefRepository(private val dao: CourtPlayerCrossRefDao) : ICourtPlayerCrossRefRepository {
    override val allCrossRefs: Flow<List<CourtPlayerCrossRef>> = dao.getAllCrossRefs()

    override fun getCrossRefsByCourtId(courtId: Int): Flow<List<CourtPlayerCrossRef>> {
        return dao.getCrossRefsByCourtId(courtId)
    }

    override fun getCrossRefsByPlayerId(playerId: Int): Flow<List<CourtPlayerCrossRef>> {
        return dao.getCrossRefsByPlayerId(playerId)
    }

    override suspend fun insert(crossRef: CourtPlayerCrossRef) {
        dao.insert(crossRef)
    }

    override suspend fun delete(crossRef: CourtPlayerCrossRef) {
        dao.delete(crossRef)
    }

    override fun getBookedDateAndHourByCourtId(courtId: Int): Flow<List<String>> {
        return dao.getBookedDateAndHourByCourtId(courtId)
    }
}