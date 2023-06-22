package com.padeltournaments.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.padeltournaments.data.entities.relations.CourtPlayerCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface CourtPlayerCrossRefDao {
    @Insert
    suspend fun insert(crossRef: CourtPlayerCrossRef)

    @Delete
    suspend fun delete(crossRef: CourtPlayerCrossRef)

    @Query("SELECT * FROM court_player")
    fun getAllCrossRefs(): Flow<List<CourtPlayerCrossRef>>

    @Query("SELECT * FROM court_player WHERE courtId = :courtId")
    fun getCrossRefsByCourtId(courtId: Int): Flow<List<CourtPlayerCrossRef>>

    @Query("SELECT * FROM court_player WHERE playerId = :playerId")
    fun getCrossRefsByPlayerId(playerId: Int): Flow<List<CourtPlayerCrossRef>>

    @Query("SELECT bookedDateAndHour FROM court_player WHERE courtId = :courtId")
    fun getBookedDateAndHourByCourtId(courtId: Int): Flow<List<String>>

}