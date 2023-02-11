package com.padeltournaments.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "organizer",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        childColumns = ["userId"],
        parentColumns = ["id"]
    )])
data class OrganizerEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var cif : String,
    var clubName : String,
    var bankAccount : String,
    var userId : Int
    //torneosOrganizados
)