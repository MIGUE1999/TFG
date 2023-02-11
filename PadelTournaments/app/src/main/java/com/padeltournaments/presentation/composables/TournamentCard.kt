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
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel

@Composable
fun TournamentCard(isOrganizer : Boolean,
                   tournament : TournamentEntity,
                   navController: NavHostController,
                   homeOrganizerViewModel: HomeOrganizerViewModel = hiltViewModel()
){
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    ){
        //Spacer()
        Row(modifier= Modifier.padding(8.dp)){
            Box(modifier = Modifier.size(120.dp)) {
                tournament.poster?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "TournamentPoster",
                        modifier = Modifier
                            .padding()
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )
                }
            }
            //Spacer()
            Column(
                Modifier.weight(1f),
            ){
                ClickableText(text= AnnotatedString("Nombre Torneo:" + tournament.name),
                    style= MaterialTheme.typography.h5,
                    onClick = {
                        Log.d("ClickableText", "SE METE EN EL ONCLICK")
                        //tournamentViewModel.onActualTournamentChanged(tournament)
                        //tournamentViewModel.getTournamentAtributes(tournament)
                        navController.navigate(NavigationScreens.TournamentDetail.route)
                    })
                Text("FechaInicio: " + tournament.startDate)
                Text("FechaFin:" + tournament.endDate)
                Text("Categoria" + tournament.category)
                Text("Premio" + tournament.prize)
            }
            //Spacer()
            if(isOrganizer) {
                IconButton(onClick = {
                    //tournamentViewModel.onActualTournamentChanged(tournament)
                    //tournamentViewModel.getTournamentAtributes(tournament)
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