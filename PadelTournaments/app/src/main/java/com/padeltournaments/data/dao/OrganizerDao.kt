package com.padeltournaments.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.padeltournaments.data.entities.OrganizerEntity
import com.padeltournaments.data.entities.relations.OrganizerWithTournaments

@Dao
interface OrganizerDao {
    @Query("SELECT * FROM organizer")
    fun getAllOrganizers() : LiveData<List<OrganizerEntity>>

    @Query("SELECT * FROM organizer WHERE id = :idOrganizer")
    fun getOrganizerById(idOrganizer: Int) : OrganizerEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrganizer(organizerEntity: OrganizerEntity)

    @Update
    suspend fun updateOrganizer(organizerEntity: OrganizerEntity)

    @Delete
    suspend fun deleteOrganizer(organizerEntity: OrganizerEntity)

    @Query("SELECT * FROM organizer WHERE userId = :idUser")
    fun getOrganizerByUserId(idUser: Int) : OrganizerEntity

    /*
    @Transaction
    @Query("SELECT * FROM organizador INNER JOIN torneo ON organizador.id = torneo.idOrganizator")
    fun getOrganizatorWithTournaments() : LiveData<List<OrganizatorWithTournaments>>
*/

    @Transaction
    @Query("SELECT * FROM organizer " +
            "INNER JOIN tournament ON organizer.id = tournament.idOrganizer " +
            "WHERE organizer.id = :idOr")
    fun getOrganizerWithTournaments(idOr : Int) : List<OrganizerWithTournaments>

    @Transaction
    @Query("SELECT * FROM organizer, tournament WHERE tournament.category = :category")
    fun getTournamentsByCategory(category: String) : LiveData<List<OrganizerWithTournaments>>


    /*
    @Transaction
    @Query("SELECT * FROM organizador WHERE id = :idOrganizator")
    suspend fun getorganizatorWithTournaments(idOrganizator: Int) : List<OrganizatorWithTournaments>
*/
}