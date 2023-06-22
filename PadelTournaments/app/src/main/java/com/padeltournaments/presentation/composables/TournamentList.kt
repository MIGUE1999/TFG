package com.padeltournaments.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.R
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.relations.CourtPlayerCrossRef
import com.padeltournaments.presentation.viewmodels.CourtDetailViewModel
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
                            CourtCard(
                                isOrganizer = true,
                                court,
                                navController,
                                idUser = idUser
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
        } else {
            if (isOrganizer) {
                LazyColumn {
                    items(items = courts) { court ->
                        val clubName by createCourtViewModel.getClubNameByOrganizerId(court.organizerId).collectAsState(initial = "")

                        CourtSearchCard(
                            isOrganizer = false,
                            court,
                            navController, createCourtViewModel, clubName)

                    }
                }
            } else {
                LazyColumn {
                    items(items = courts) { court ->
                        val clubName by createCourtViewModel.getClubNameByOrganizerId(court.organizerId).collectAsState(initial = "")
                        CourtSearchCard(isOrganizer = false, court, navController, createCourtViewModel, clubName)
                    }
                }
            }
        }
    }

}

@Composable
fun CourtPlayerCrossRefList(
              courtsPlayerCrossRef : List<CourtPlayerCrossRef>?,
) {

    if (courtsPlayerCrossRef != null) {
        LazyColumn {
            items(items = courtsPlayerCrossRef) { courtPlayerCross ->

                CourtPlayerCrossRefCard(idCourt = courtPlayerCross.courtId, date = courtPlayerCross.bookedDateAndHour)
            }
        }

    }

}

@Composable
fun CourtPlayerCrossRefCard(
              idCourt : Int,
              date: String,
              courtDetailViewModel: CourtDetailViewModel = hiltViewModel(),
){

    LaunchedEffect(Unit) {
        courtDetailViewModel.setCourtById(idCourt)
    }

    Card(shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    )
    {
        Column() {
            Card(modifier = Modifier.fillMaxWidth(),
                elevation = 18.dp) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                    ) {

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(painter = painterResource(id = R.drawable.ubicacion),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(3)
                            Text(courtDetailViewModel.ubication.value)
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text("Numero de pista: " + courtDetailViewModel.courtNumber.value)
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text("Fecha de la reserva: $date")
                        }
                    }

                }
            }
        }
    }
}