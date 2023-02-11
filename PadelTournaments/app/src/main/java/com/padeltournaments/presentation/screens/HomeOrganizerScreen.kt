package com.padeltournaments.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.Spacer
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.FAB
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens
import com.padeltournaments.presentation.composables.TournamentList


@Composable
fun HomeOrganizerScreen(session : LoginPref,
                        homeOrganizerViewModel : HomeOrganizerViewModel = hiltViewModel(),
                        navController : NavHostController)
{

    val orgId = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()
    Log.d("HomeScreen", "IdOrg: $orgId")

    val tournamentsByOrgId = homeOrganizerViewModel.getTournamentByOrgId(orgId)
        .collectAsState(initial = emptyList())

    Scaffold(topBar = { TopBar()},
             bottomBar = { BottomBar(navController = navController, organizerScreens) },
             content = { HomeOrganizerContent(navController = navController, tournamentsByOrgId.value)},
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



