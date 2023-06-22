package com.padeltournaments.data.entities.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.PlayerEntity


@Entity(tableName = "court_player",
    foreignKeys = [
        ForeignKey(
            entity = CourtEntity::class,
            parentColumns = ["id"],
            childColumns = ["courtId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CourtPlayerCrossRef(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val courtId: Int,
    val playerId: Int,
    val bookedDateAndHour: String
)