package com.padeltournaments.data.repository.implementation

import androidx.lifecycle.LiveData
import com.padeltournaments.data.dao.OrganizerDao
import com.padeltournaments.data.entities.OrganizerEntity
import com.padeltournaments.data.entities.relations.OrganizerWithTournaments
import com.padeltournaments.data.repository.interfaces.IOrganizerRepository
import javax.inject.Inject
class OrganizerRepositoryImpl @Inject constructor(private val organizerDao : OrganizerDao): IOrganizerRepository {
    override fun getAllOrganizers(): LiveData<List<OrganizerEntity>> {
        return organizerDao.getAllOrganizers()
    }
    override fun getOrganizerById(idOrganizer: Int): OrganizerEntity? {
        return organizerDao.getOrganizerById(idOrganizer)
    }
    override suspend fun insertOrganizer(organizadorEntity: OrganizerEntity) {
        organizerDao.insertOrganizer(organizadorEntity)
    }
    override suspend fun updateOrganizer(organizator: OrganizerEntity) {
        organizerDao.updateOrganizer(organizator)
    }
    override suspend fun deleteOrganizer(organizer: OrganizerEntity) {
        organizerDao.deleteOrganizer(organizer)
    }
    override fun getOrganizerWithTournaments(idOrganizer:Int): List<OrganizerWithTournaments> {
        return organizerDao.getOrganizerWithTournaments(idOrganizer)
    }
    override fun getOrganizerByUserId(userId: Int): OrganizerEntity {
        return organizerDao.getOrganizerByUserId(userId)
    }
}