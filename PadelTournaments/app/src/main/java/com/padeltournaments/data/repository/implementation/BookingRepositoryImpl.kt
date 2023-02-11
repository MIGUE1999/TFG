package com.padeltournaments.data.repository.implementation

import com.padeltournaments.data.dao.BookingDao
import com.padeltournaments.data.entities.BookingEntity
import com.padeltournaments.data.repository.interfaces.IBookingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(private val bookingDao : BookingDao) : IBookingRepository  {
    override fun getAllBookings(): Flow<List<BookingEntity>> {
        return bookingDao.getAllReserva()
    }

    override fun getBookingById(idReserva: Int): Flow<BookingEntity> {
        return bookingDao.getReservaById(idReserva)
    }

    override suspend fun insertBooking(reservaEntity: BookingEntity) {
        bookingDao.insertReserva(reservaEntity)
    }

    override suspend fun insertBookings(reservaEntities: List<BookingEntity>) {
        bookingDao.insertReservas(reservaEntities)
    }

    override suspend fun updateBooking(reservaEntity: BookingEntity) {
        bookingDao.updateReserva(reservaEntity)
    }

    override suspend fun deleteBooking(reservaEntity: BookingEntity) {
        bookingDao.deleteReserva(reservaEntity)
    }

    override suspend fun deleteBookings(reservaEntities: List<BookingEntity>) {
        bookingDao.deletereservas(reservaEntities)
    }
}