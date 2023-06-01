package com.padeltournaments.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var password: String,
    var email: String,
    var telephoneNumber: String,
    var surname: String,
    var rol : String
)