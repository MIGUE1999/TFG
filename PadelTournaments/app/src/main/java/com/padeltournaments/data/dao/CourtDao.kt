package com.padeltournaments.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.padeltournaments.data.entities.CourtEntity
import kotlinx.coroutines.flow.Flow

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
    @Query("SELECT * FROM court WHERE organizerId IN (SELECT id FROM organizer WHERE userId = :idUser)")
    suspend fun getCourtsByUserId(idUser: Int): Flow<List<CourtEntity>>
    @Query("SELECT organizer.clubName FROM court JOIN organizer ON court.organizerId = organizer.id WHERE organizer.userId = :idUser")
    suspend fun getClubNameByUserId(idUser: Int): String
}