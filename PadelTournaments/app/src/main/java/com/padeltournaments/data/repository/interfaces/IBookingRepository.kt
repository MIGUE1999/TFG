package com.padeltournaments.data.repository.interfaces

import com.padeltournaments.data.entities.BookingEntity
import kotlinx.coroutines.flow.Flow

interface IBookingRepository {

    fun getAllBookings() : Flow<List<BookingEntity>>

    fun getBookingById(idReserva: Int) : Flow<BookingEntity>

    suspend fun insertBooking(reservaEntity: BookingEntity)

    suspend fun insertBookings(reservaEntities: List<BookingEntity>)

    suspend fun updateBooking(reservaEntity: BookingEntity)

    suspend fun deleteBooking(reservaEntity: BookingEntity)

    suspend fun deleteBookings(reservaEntities: List<BookingEntity>)
}