package com.padeltournaments.data.dao

import androidx.room.*
import com.padeltournaments.data.entities.InscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InscriptionDao {
    @Query("SELECT * FROM Inscription")
    fun getAllInscripcion() : Flow<List<InscriptionEntity>>

    @Query("SELECT * FROM Inscription WHERE id = :idInscripcion")
    fun getInscripcionById(idInscripcion: Int) : Flow<InscriptionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInscripcion(inscripcionEntity: InscriptionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInscripciones(inscripcionEntity: List<InscriptionEntity>)

    @Update
    suspend fun updateInscripcion(inscripcionEntity: InscriptionEntity)

    @Delete
    suspend fun deleteInscripcion(inscripcionEntity: InscriptionEntity)

    @Delete
    suspend fun deleteInscripciones(inscripciones: List<InscriptionEntity>)
}
