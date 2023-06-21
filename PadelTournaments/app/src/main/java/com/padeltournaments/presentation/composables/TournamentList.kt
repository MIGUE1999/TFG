package com.padeltournaments.presentation.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.util.LoginPref

@Composable
fun TournamentList(navController: NavHostController,
                   isOrganizer : Boolean,
                   tournaments : List<TournamentEntity>?
) {
    if (tournaments != null)
    {
        if (isOrganizer) {
            LazyColumn{
                items(items = tournaments) { tournament ->
                    TournamentCard(
                        isOrganizer = true,
                        tournament,
                        navController
                    )
                }
            }
        } else {
            LazyColumn{
                items(items = tournaments) { tournament ->
                    TournamentCard(isOrganizer = false, tournament, navController)
                }
            }
        }
    }
}


@Composable
fun CourtList(navController: NavHostController,
                   isOrganizer : Boolean,
                   courts : List<CourtEntity>?,
                   session: LoginPref
) {

    session.getUserDetails()[LoginPref.KEY_ORG_ID]?.let {
        val idUser = it.toInt()

        if (courts != null) {
            if (isOrganizer) {
                LazyColumn {
                    items(items = courts) { court ->
                        CourtCard(
                            isOrganizer = true,
                            court,
                            navController,
                            idUser= idUser
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(items = courts) { court ->
                        CourtCard(isOrganizer = false, court, navController, idUser = idUser)
                    }
                }
            }
        }
    }
}
