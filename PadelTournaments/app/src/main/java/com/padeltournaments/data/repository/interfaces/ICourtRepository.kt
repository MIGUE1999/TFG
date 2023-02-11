package com.padeltournaments.data.repository.interfaces

import androidx.lifecycle.LiveData
import com.padeltournaments.data.entities.CourtEntity

interface ICourtRepository {

    fun getAllCourts() : LiveData<List<CourtEntity>>

    fun getCourtById(idCourt: Int) : LiveData<CourtEntity>

    suspend fun insertCourt(courtEntity: CourtEntity)

    suspend fun updateCourt(courtEntity: CourtEntity)

    suspend fun deleteCourt(courtEntity: CourtEntity)
}