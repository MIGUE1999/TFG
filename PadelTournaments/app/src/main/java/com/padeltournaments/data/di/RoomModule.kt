package com.padeltournaments.data.di

import android.content.Context
import androidx.room.Room
import com.padeltournaments.data.database.AppDatabase
import com.padeltournaments.data.repository.implementation.*
import com.padeltournaments.data.repository.interfaces.*
import com.padeltournaments.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    @Provides
    @Singleton
    fun provideUserDao(db:AppDatabase) = db.userDao
    @Provides
    @Singleton
    fun provideUserRepository(db:AppDatabase) : IUserRepository {
        return UserRepositoryImpl(db.userDao)
    }
    @Provides
    @Singleton
    fun provideTournamentRepository(db:AppDatabase) : ITournamentRepository {
        return TournamentRepositoryImpl(db.tournamentDao)
    }
    @Provides
    @Singleton
    fun provideBookingRepository(db:AppDatabase) : IBookingRepository {
        return BookingRepositoryImpl(db.bookingDao)
    }
    @Provides
    @Singleton
    fun provideCourtRepository(db:AppDatabase) : ICourtRepository {
        return CourtRepositoryImpl(db.courtDao)
    }
    @Provides
    @Singleton
    fun provideOrganizerRepository(db:AppDatabase) : IOrganizerRepository {
        return OrganizerRepositoryImpl(db.organizerDao)
    }
    @Provides
    @Singleton
    fun providePlayerRepository(db:AppDatabase) : IPlayerRepository {
        return PlayerRepositoryImpl(db.playerDao)
    }
    @Provides
    @Singleton
    fun provideInscriptionRepository(db:AppDatabase) : IInscriptionRepository {
        return InscriptionRepositoryImpl(db.inscriptionDao)
    }
    @Provides
    @Singleton
    fun provideTournamentPlayerRepository(db:AppDatabase) : ITournamentPlayerRelationRepository {
        return TournamentPlayerRelationRepositoryImpl(db.tournamentPlayerRelationDao)
    }
    @Provides
    @Singleton
    fun provideCourtPlayerCrossRefRepository(db:AppDatabase) : ICourtPlayerCrossRefRepository {
        return CourtPlayerCrossRefRepository(db.courtPlayerCrossRefDao)
    }
}