package com.padeltournaments.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.Spacer
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.FAB
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens
import com.padeltournaments.presentation.composables.TournamentList
@Composable
fun HomeOrganizerScreen(session : LoginPref,
                        homeOrganizerViewModel : HomeOrganizerViewModel = hiltViewModel(),
                        navController : NavHostController)
{
    val userId = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

    homeOrganizerViewModel.setUserId(userId)

    val tournaments by homeOrganizerViewModel.tournamentsByUserId.collectAsState(emptyList())

    Scaffold(topBar = { TopBar()},
             bottomBar = { BottomBar(navController = navController, organizerScreens) },
             content = { HomeOrganizerContent(navController = navController, tournaments)},
             floatingActionButton = { FAB(navController = navController) }
        )
}
@Composable
fun HomeOrganizerContent(navController: NavHostController, tournaments : List<TournamentEntity>){
    Column(horizontalAlignment = CenterHorizontally,
        modifier = Modifier.fillMaxHeight(0.9f).fillMaxWidth())
    {
        Text(text = "Tus torneos", style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(16.dp))
        Spacer()
        TournamentList(isOrganizer = true, navController = navController, tournaments = tournaments)
    }
}





