package com.padeltournaments.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.padeltournaments.data.entities.PlayerEntity

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player")
    fun getAllPlayers() : LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE id = :idJugador")
    fun getPlayerById(idJugador : Int) : LiveData<PlayerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Delete
    suspend fun deleteJugador(player: PlayerEntity)

    @Query("SELECT * FROM player WHERE userId = :idUser")
    fun getPlayerByUserId(idUser: Int) : PlayerEntity


}