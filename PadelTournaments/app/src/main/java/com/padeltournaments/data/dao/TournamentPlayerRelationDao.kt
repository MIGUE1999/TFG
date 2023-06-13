package com.padeltournaments.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.data.entities.relations.TournamentPlayerRelation
import kotlinx.coroutines.flow.Flow
@Dao
interface TournamentPlayerRelationDao {
    @Insert
    suspend fun insert(tournamentPlayerRelation: TournamentPlayerRelation)

    @Delete
    fun delete(tournamentPlayerRelation: TournamentPlayerRelation)

    @Query("SELECT * FROM tournament INNER JOIN tournament_player_relation ON tournament.id = tournament_player_relation.tournamentId WHERE tournament_player_relation.playerId = :playerId")
    fun getTournamentsForPlayer(playerId: Int): List<TournamentEntity>

    @Query("SELECT * FROM user INNER JOIN player ON user.id = player.userId INNER JOIN tournament_player_relation ON player.id = tournament_player_relation.playerId WHERE tournament_player_relation.tournamentId = :tournamentId")
    fun getUsersForTournament(tournamentId: Int): Flow<List<UserEntity>>

    @Query("SELECT * FROM tournament WHERE id IN (SELECT tournamentId FROM tournament_player_relation WHERE playerId IN (SELECT id FROM player WHERE userId = :userId))")
    fun getTournamentsByUserId(userId: Int): Flow<List<TournamentEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM tournament_player_relation INNER JOIN player ON player.id = tournament_player_relation.playerId WHERE player.userId = :userId AND tournament_player_relation.tournamentId = :tournamentId LIMIT 1)")
    fun isUserInTournament(userId: Int, tournamentId: Int): Flow<Boolean>

}