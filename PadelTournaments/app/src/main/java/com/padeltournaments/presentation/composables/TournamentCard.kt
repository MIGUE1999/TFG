package com.padeltournaments.presentation.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.TournamentEntity
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
@Composable
fun TournamentCard(isOrganizer : Boolean,
                   tournament : TournamentEntity,
                   navController: NavHostController,
                   homeOrganizerViewModel: HomeOrganizerViewModel = hiltViewModel(),
                   createTournamentViewModel: CreateTournamentViewModel = hiltViewModel()
){
    Card(shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth())
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
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth(fraction = 0.8f)) {
                        ClickableText(text = AnnotatedString(tournament.name),
                            style = MaterialTheme.typography.h5,
                            onClick = {
                                Log.d("ClickableText", "SE METE EN EL ONCLICK")
                                //tournamentViewModel.onActualTournamentChanged(tournament)
                                //tournamentViewModel.getTournamentAtributes(tournament)
                                navController.navigate(NavigationScreens.TournamentDetail.route)
                            })
                        Text("Fecha de inicio: " + tournament.startDate)
                        Text("Fecha de finalizacion:" + tournament.endDate)
                        Text("Categoria: " + tournament.category)
                        Text("Premio: " + tournament.prize)
                    }
                    if (isOrganizer) {
                        IconButton(onClick = {
                            //tournamentViewModel.onActualTournamentChanged(tournament)
                            //tournamentViewModel.getTournamentAtributes(tournament)
                            createTournamentViewModel.setTournament(tournament)
                            navController.navigate(NavigationScreens.EditTournament.route)
                        }) {
                            Icon(Icons.Filled.Edit, "EditTournament")
                        }
                        IconButton(onClick = {
                            homeOrganizerViewModel.deleteTournament(tournament)
                        }) {
                            Icon(Icons.Filled.Delete, "DeleteTournament")
                        }
                    }
                }
            }
        }
    }
}