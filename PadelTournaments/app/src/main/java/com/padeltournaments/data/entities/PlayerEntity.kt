package com.padeltournaments.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(tableName = "player",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        childColumns = ["userId"],
        parentColumns = ["id"]
    )])
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nickname : String,
    var userId : Int
)