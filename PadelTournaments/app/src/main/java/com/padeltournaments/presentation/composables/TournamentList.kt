package com.padeltournaments.presentation.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel
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
                   idUser: Int,
                    isSearch: Boolean,
              createCourtViewModel: CreateCourtViewModel = hiltViewModel()
) {

    if (courts != null) {
        if(!isSearch) {
            if (isOrganizer) {
                LazyColumn {
                    items(items = courts) { court ->
                        if (idUser != null) {
                            CourtCard(
                                isOrganizer = true,
                                court,
                                navController,
                                idUser = idUser
                            )
                        }
                    }
                }
            } else {
                LazyColumn {
                    items(items = courts) { court ->
                        CourtCard(isOrganizer = false, court, navController, idUser = idUser)
                    }
                }
            }
        } else {
            if (isOrganizer) {
                LazyColumn {
                    items(items = courts) { court ->
                        createCourtViewModel.getClubNameByOrganizerId(court.organizerId)

                            CourtSearchCard(
                                isOrganizer = true,
                                court,
                                navController, createCourtViewModel )

                    }
                }
            } else {
                LazyColumn {
                    items(items = courts) { court ->
                        createCourtViewModel.getClubNameByOrganizerId(court.organizerId)
                        CourtSearchCard(isOrganizer = false, court, navController, createCourtViewModel)
                    }
                }
            }
        }
    }

}
