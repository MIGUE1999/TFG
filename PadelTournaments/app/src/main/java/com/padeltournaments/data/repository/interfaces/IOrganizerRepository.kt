package com.padeltournaments.data.repository.interfaces

import androidx.lifecycle.LiveData
import com.padeltournaments.data.entities.OrganizerEntity
import com.padeltournaments.data.entities.relations.OrganizerWithTournaments
import kotlinx.coroutines.flow.Flow

interface IOrganizerRepository {
    fun getAllOrganizers() : LiveData<List<OrganizerEntity>>
    fun getOrganizerById(idOrganizer: Int) : OrganizerEntity?
    suspend fun insertOrganizer(organizerEntity: OrganizerEntity)
    suspend fun updateOrganizer(organizerEntity: OrganizerEntity)
    suspend fun deleteOrganizer(organizerEntity: OrganizerEntity)
    fun getOrganizerWithTournaments(idOrganizer : Int) : List<OrganizerWithTournaments>
    fun getOrganizerByUserId(userId : Int) : OrganizerEntity
    fun getClubNameByOrganizerId(idOrganizer: Int): Flow<String>
}