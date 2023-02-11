package com.padeltournaments.data.dao

import androidx.room.*
import com.padeltournaments.data.entities.BookingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Query("SELECT * FROM booking")
    fun getAllReserva() : Flow<List<BookingEntity>>

    @Query("SELECT * FROM booking WHERE id = :idReserva")
    fun getReservaById(idReserva: Int) : Flow<BookingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserva(reservaEntity: BookingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservas(reservaEntities: List<BookingEntity>)

    @Update
    suspend fun updateReserva(reservaEntity: BookingEntity)

    @Delete
    suspend fun deleteReserva(reservaEntity: BookingEntity)

    @Delete
    suspend fun deletereservas(reservaEntities: List<BookingEntity>)
}