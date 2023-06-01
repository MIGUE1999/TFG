package com.padeltournaments.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.padeltournaments.data.entities.TournamentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentDao  {
    @Query("SELECT * FROM tournament")
    fun getAll(): Flow<List<TournamentEntity>>
    @Query("SELECT * FROM tournament")
    fun getAllTournamentsStatic(): List<TournamentEntity>
    @Query("SELECT * FROM tournament WHERE id = :idTournament")
    fun getById(idTournament: Int) : LiveData<TournamentEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournament(tournamentEntity: TournamentEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournaments(tournamentEntities: List<TournamentEntity>)
    @Update
    suspend fun updateTournament(tournamentEntity: TournamentEntity)
    @Delete
    suspend fun deleteTournament(tournamentEntity: TournamentEntity)
    @Delete
    suspend fun deleteTournaments(tournamentEntities: List<TournamentEntity>)
    @Query("SELECT * FROM tournament WHERE idOrganizer = :idOrg")
    fun getTournamentsByOrgId(idOrg: Int) : Flow<List<TournamentEntity>>
}
