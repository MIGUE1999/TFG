package com.padeltournaments.data.repository.interfaces

import com.padeltournaments.data.entities.InscriptionEntity
import kotlinx.coroutines.flow.Flow

interface IInscriptionRepository {
    fun getAllInscription() : Flow<List<InscriptionEntity>>
    fun getInscriptionById(idInscription: Int) : Flow<InscriptionEntity>
    suspend fun insertInscription(inscriptionEntity: InscriptionEntity)
    suspend fun insertInscriptions(inscriptionEntity: List<InscriptionEntity>)
    suspend fun updateInscription(inscriptionEntity: InscriptionEntity)
    suspend fun deleteInscription(inscriptionEntity: InscriptionEntity)
    suspend fun deleteInscriptions(inscriptions: List<InscriptionEntity>)
}