package com.padeltournaments.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.padeltournaments.data.dao.*
import com.padeltournaments.data.entities.*
import com.padeltournaments.util.Converters
@Database(entities = [UserEntity::class, PlayerEntity::class, OrganizerEntity::class,
    TournamentEntity::class, CourtEntity::class,
    InscriptionEntity::class, BookingEntity::class,
    //OrganizatorWithTournaments::class
],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    abstract val tournamentDao : TournamentDao
    abstract val playerDao : PlayerDao
    abstract val organizerDao : OrganizerDao
    abstract val courtDao: CourtDao
    abstract val inscriptionDao : InscriptionDao
    abstract val bookingDao : BookingDao
    //abstract val organizatorWithTournaments : OrganizatorWithTournaments
}