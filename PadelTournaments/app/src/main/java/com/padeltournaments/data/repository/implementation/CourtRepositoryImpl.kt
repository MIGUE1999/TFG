package com.padeltournaments.data.repository.implementation

import androidx.lifecycle.LiveData
import com.padeltournaments.data.dao.CourtDao
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.repository.interfaces.ICourtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class CourtRepositoryImpl @Inject constructor(private val courtDao : CourtDao): ICourtRepository {
    override fun getAllCourts(): Flow<List<CourtEntity>> {
        return courtDao.getAllCourts()
    }
    override fun getCourtById(idCourt: Int): CourtEntity {
        return courtDao.getCourtById(idCourt)
    }
    override suspend fun insertCourt(courtEntity: CourtEntity) {
        courtDao.insertCourt(courtEntity)
    }
    override suspend fun updateCourt(courtEntity: CourtEntity) {
        courtDao.updateCourt(courtEntity)
    }
    override suspend fun deleteCourt(courtEntity: CourtEntity) {
        courtDao.deleteCourt(courtEntity)
    }
    override suspend fun getClubNameByUserId(idUser: Int): String{
        return courtDao.getClubNameByUserId(idUser)
    }

    override fun getCourtsByUserId(idUser: Int): Flow<List<CourtEntity>> {
        return courtDao.getCourtsByUserId(idUser)
    }
}