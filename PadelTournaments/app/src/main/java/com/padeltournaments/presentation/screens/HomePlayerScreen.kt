package com.padeltournaments.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.Spacer
import com.padeltournaments.presentation.composables.TournamentList
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.playerScreens
@Composable
fun HomePlayerScreen(session : LoginPref,
                     homeOrganizerViewModel : HomeOrganizerViewModel = hiltViewModel(),
                     navController : NavHostController) {
    val userId = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

    homeOrganizerViewModel.setUserId(userId)

    val tournaments by homeOrganizerViewModel.tournamentsPlayerByUserId.collectAsState(emptyList())

    Scaffold(topBar = { TopBar()},
        bottomBar = { BottomBar(navController = navController, playerScreens) },
        content = { HomePlayerContent(navController = navController, tournaments)},
    )
}

@Composable
fun HomePlayerContent(navController: NavHostController, tournaments : List<TournamentEntity>){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight(0.9f).fillMaxWidth())
    {
        Text(text = "Tus Inscripciones", style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(16.dp))
        Spacer()
        TournamentList(isOrganizer = false, navController = navController, tournaments = tournaments)
    }
}