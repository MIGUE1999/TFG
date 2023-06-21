package com.padeltournaments.data.repository.interfaces

import androidx.lifecycle.LiveData
import com.padeltournaments.data.entities.CourtEntity
import kotlinx.coroutines.flow.Flow

interface ICourtRepository {
    fun getAllCourts() : Flow<List<CourtEntity>>
    fun getCourtById(idCourt: Int) : CourtEntity
    suspend fun insertCourt(courtEntity: CourtEntity)
    suspend fun updateCourt(courtEntity: CourtEntity)
    suspend fun deleteCourt(courtEntity: CourtEntity)
    suspend fun getClubNameByUserId(idUser: Int): String
    fun getCourtsByUserId(idUser: Int): Flow<List<CourtEntity>>
}