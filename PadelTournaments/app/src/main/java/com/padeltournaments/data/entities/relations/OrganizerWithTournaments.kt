package com.padeltournaments.data.entities.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.padeltournaments.data.entities.OrganizerEntity
import com.padeltournaments.data.entities.TournamentEntity


@Entity
data class OrganizerWithTournaments (
    @Embedded val organizer: OrganizerEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idOrganizer"
    )
    val tournaments : List<TournamentEntity>
)
