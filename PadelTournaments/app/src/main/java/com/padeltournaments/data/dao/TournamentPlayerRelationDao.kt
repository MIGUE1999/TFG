package com.padeltournaments.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.relations.TournamentPlayerRelation

@Dao
interface TournamentPlayerRelationDao {
    @Insert
    suspend fun insert(tournamentPlayerRelation: TournamentPlayerRelation)

    @Delete
    fun delete(tournamentPlayerRelation: TournamentPlayerRelation)

    @Query("SELECT * FROM tournament INNER JOIN tournament_player_relation ON tournament.id = tournament_player_relation.tournamentId WHERE tournament_player_relation.playerId = :playerId")
    fun getTournamentsForPlayer(playerId: Int): List<TournamentEntity>

    @Query("SELECT * FROM player INNER JOIN tournament_player_relation ON player.id = tournament_player_relation.playerId WHERE tournament_player_relation.tournamentId = :tournamentId")
    fun getPlayersForTournament(tournamentId: Int): List<PlayerEntity>
}