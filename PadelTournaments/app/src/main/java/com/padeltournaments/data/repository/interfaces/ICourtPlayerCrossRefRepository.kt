package com.padeltournaments.data.repository.interfaces

import com.padeltournaments.data.entities.relations.CourtPlayerCrossRef
import kotlinx.coroutines.flow.Flow

interface ICourtPlayerCrossRefRepository {
    val allCrossRefs: Flow<List<CourtPlayerCrossRef>>

    fun getCrossRefsByCourtId(courtId: Int): Flow<List<CourtPlayerCrossRef>>

    fun getCrossRefsByPlayerId(playerId: Int): Flow<List<CourtPlayerCrossRef>>

    suspend fun insert(crossRef: CourtPlayerCrossRef)

    suspend fun delete(crossRef: CourtPlayerCrossRef)
}