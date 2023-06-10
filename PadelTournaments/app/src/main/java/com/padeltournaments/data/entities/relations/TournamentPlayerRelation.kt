package com.padeltournaments.data.entities.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.padeltournaments.data.entities.PlayerEntity
import com.padeltournaments.data.entities.TournamentEntity

@Entity(tableName = "tournament_player_relation",
    primaryKeys = ["tournamentId", "playerId"],
    foreignKeys = [
        ForeignKey(
            entity = TournamentEntity::class,
            parentColumns = ["id"],
            childColumns = ["tournamentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["tournamentId"]),
        Index(value = ["playerId"])
    ]
)
class TournamentPlayerRelation(
    val tournamentId: Int,
    val playerId: Int
)