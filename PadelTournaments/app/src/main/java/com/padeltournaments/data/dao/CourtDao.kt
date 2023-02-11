package com.padeltournaments.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.padeltournaments.data.entities.CourtEntity

@Dao
interface CourtDao
{
    @Query("SELECT * FROM court")
    fun getAllCourts() : LiveData<List<CourtEntity>>

    @Query("SELECT * FROM court WHERE id = :idCourt")
    fun getCourtById(idCourt: Int) : LiveData<CourtEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourt(courtEntity: CourtEntity)

    @Update
    suspend fun updateCourt(courtEntity: CourtEntity)

    @Delete
    suspend fun deleteCourt(courtEntity: CourtEntity)
}