package com.padeltournaments.presentation.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.TournamentEntity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.padeltournaments.R
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import kotlinx.coroutines.delay

@Composable
fun TournamentCard(isOrganizer : Boolean,
                   tournament : TournamentEntity,
                   navController: NavHostController,
                   homeOrganizerViewModel: HomeOrganizerViewModel = hiltViewModel(),
                   createTournamentViewModel: CreateTournamentViewModel = hiltViewModel()
){

    val euroSymbol = '\u20AC'
    Card(shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    )
    {
        Column() {
            Box(modifier = Modifier.fillMaxWidth()) {
                tournament.poster?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "TournamentPoster",
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )
                }
            }
            Card(modifier = Modifier.fillMaxWidth(),
            elevation = 18.dp) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    ) {
                    Column(modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                        ) {
                        ClickableText(text = AnnotatedString(tournament.name),
                            style = MaterialTheme.typography.h5,
                            onClick = {
                                val idTournament = tournament.id.toString()
                                navController.navigate("tournament_detail/$idTournament")
                            })
                        Spacer(4)
                        Row(modifier = Modifier.fillMaxWidth()){
                            Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                                Icon(painter = painterResource(id = R.drawable.calendario),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(3)
                                Text(tournament.startDate)
                            }

                            Spacer(3)
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Icon(painter = painterResource(id = R.drawable.calendario),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(3)
                                Text(tournament.endDate)
                            }

                        }

                        Spacer(3)
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.category),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(3)
                                Text(tournament.category)
                            }

                            Spacer(3)
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Icon(
                                    painter = painterResource(id = R.drawable.prize),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(3)
                                Text(tournament.prize.toString() + euroSymbol)
                            }
                        }

                        Spacer(3)
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ubicacion),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(3)
                                Text(tournament.province)
                            }
                        }
                        Spacer(3)

                    }

                    if (isOrganizer) {
                        IconButton(onClick = {
                            val idTournament = tournament.id.toString()
                            navController.navigate("edit_tournament/$idTournament")
                        }) {
                            Icon(Icons.Filled.Edit, "EditTournament")
                        }
                        IconButton(onClick = {
                            homeOrganizerViewModel.deleteTournament(tournament, userId = tournament.idOrganizer )
                        }) {
                            Icon(Icons.Filled.Delete, "DeleteTournament")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourtCard(isOrganizer : Boolean,
            court : CourtEntity,
            navController: NavHostController,
            createCourtViewModel: CreateCourtViewModel = hiltViewModel(),
            idUser: Int
){



    createCourtViewModel.getClubNameByUserId(idUser)


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
                        ClickableText(text = AnnotatedString(createCourtViewModel.clubNameState.value.uppercase() + " PISTA " + court.courtNumber),
                            style = MaterialTheme.typography.h5,
                            onClick = {
                                val idCourt = court.id.toString()
                                navController.navigate("court_detail/$idCourt")
                            })
                        Spacer(4)

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(painter = painterResource(id = R.drawable.ubicacion),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(3)
                            Text(court.ubication)
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(painter = painterResource(id = R.drawable.ubicacion),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(3)
                            Text(court.province)
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(painter = painterResource(id = R.drawable.money),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(3)
                            Text(court.bookCost.toString() + '\u20AC')
                        }
                    }

                    if (isOrganizer) {
                        IconButton(onClick = {
                            val idCourt = court.id.toString()
                            navController.navigate("edit_tournament/$idCourt")
                        }) {
                            Icon(Icons.Filled.Edit, "EditCourt")
                        }
                        IconButton(onClick = {
                            createCourtViewModel.deleteCourt(court)
                        }) {
                            Icon(Icons.Filled.Delete, "DeleteCourt")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourtSearchCard(isOrganizer : Boolean,
              court : CourtEntity,
              navController: NavHostController,
              createCourtViewModel: CreateCourtViewModel,
                    clubName: String
){

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

                        ClickableText(text = AnnotatedString(text = clubName.uppercase() +" PISTA " + court.courtNumber),
                            style = MaterialTheme.typography.h5,
                            onClick = {
                                val idCourt = court.id.toString()
                                navController.navigate("court_detail/$idCourt")
                            })
                        Spacer(4)

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(painter = painterResource(id = R.drawable.ubicacion),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(3)
                            Text(court.ubication)
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(painter = painterResource(id = R.drawable.ubicacion),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(3)
                            Text(court.province)
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(painter = painterResource(id = R.drawable.money),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(3)
                            Text(court.bookCost.toString() + '\u20AC')
                        }
                    }

                    if (isOrganizer) {
                        IconButton(onClick = {
                            val idCourt = court.id.toString()
                            navController.navigate("edit_tournament/$idCourt")
                        }) {
                            Icon(Icons.Filled.Edit, "EditCourt")
                        }
                        IconButton(onClick = {
                            createCourtViewModel.deleteCourt(court)
                        }) {
                            Icon(Icons.Filled.Delete, "DeleteCourt")
                        }
                    }
                }
            }
        }
    }
}

