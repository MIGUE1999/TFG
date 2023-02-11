package com.padeltournaments.data.repository.implementation

import com.padeltournaments.data.dao.InscriptionDao
import com.padeltournaments.data.entities.InscriptionEntity
import com.padeltournaments.data.repository.interfaces.IInscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InscriptionRepositoryImpl @Inject constructor(private val inscriptionDao: InscriptionDao): IInscriptionRepository {
    override fun getAllInscription(): Flow<List<InscriptionEntity>> {
        return inscriptionDao.getAllInscripcion()
    }

    override fun getInscriptionById(idInscripcion: Int): Flow<InscriptionEntity> {
        return inscriptionDao.getInscripcionById(idInscripcion)
    }

    override suspend fun insertInscription(inscripcionEntity: InscriptionEntity) {
        inscriptionDao.insertInscripcion(inscripcionEntity)
    }

    override suspend fun insertInscriptions(inscripcionEntity: List<InscriptionEntity>) {
        inscriptionDao.insertInscripciones(inscripcionEntity)
    }

    override suspend fun updateInscription(inscripcionEntity: InscriptionEntity) {
        inscriptionDao.updateInscripcion(inscripcionEntity)
    }

    override suspend fun deleteInscription(inscripcionEntity: InscriptionEntity) {
        inscriptionDao.deleteInscripcion(inscripcionEntity)
    }

    override suspend fun deleteInscriptions(inscripciones: List<InscriptionEntity>) {
        inscriptionDao.deleteInscripciones(inscripciones)
    }
}