package com.padeltournaments.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.entities.relations.CourtPlayerCrossRef
import com.padeltournaments.presentation.composables.CourtList
import com.padeltournaments.presentation.composables.CourtPlayerCrossRefList
import com.padeltournaments.presentation.composables.Spacer
import com.padeltournaments.presentation.composables.TournamentList
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import com.padeltournaments.presentation.viewmodels.SearchViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens
import com.padeltournaments.util.playerScreens
@Composable
fun HomePlayerScreen(session : LoginPref,
                     homeOrganizerViewModel : HomeOrganizerViewModel = hiltViewModel(),
                     createCourtViewModel: CreateCourtViewModel = hiltViewModel(),
                     navController : NavHostController) {
    val userId = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

    homeOrganizerViewModel.setUserId(userId)

    val tournaments by homeOrganizerViewModel.tournamentsPlayerByUserId.collectAsState(emptyList())
    val courts by createCourtViewModel.courtsByUserId.collectAsState(emptyList())

    Scaffold(
        bottomBar = { BottomBar(navController = navController, playerScreens) },
        content = {
            TabBarPlayer(navController = navController, tournaments = tournaments,
                courts = courts, homeOrganizerViewModel = homeOrganizerViewModel, idUser = userId,
            )
        },
    )
}

@Composable
fun HomePlayerContent(navController: NavHostController, tournaments : List<TournamentEntity>){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth())
    {
        Spacer()
        TournamentList(isOrganizer = false, navController = navController, tournaments = tournaments)
    }
}

@Composable
fun TabBarPlayer(navController : NavHostController,
                tournaments: List<TournamentEntity>,
                courts: List<CourtEntity>,
                homeOrganizerViewModel: HomeOrganizerViewModel,
                idUser: Int,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .height(100.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = "INSCRIPCIONES",
                        modifier = Modifier.weight(1f)
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = "PISTAS RESERVADAS",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        // Aquí puedes agregar el contenido correspondiente a cada pestaña

        if (selectedTabIndex == 0) {
            homeOrganizerViewModel.isTournamentList.value = true
            HomePlayerContent(navController = navController, tournaments)
        } else {
            homeOrganizerViewModel.isTournamentList.value = false
            val crossRefs by homeOrganizerViewModel.crossRefsByPlayerId.collectAsState()

            // Obtener la lista de CourtPlayerCrossRef por UserId
            homeOrganizerViewModel.getCrossRefsByUserId(idUser)
            HomePlayerContentCourt(
                navController = navController,
                courtPlayerCrossRefsList = crossRefs,
                idUser = idUser
            )
        }

    }

}

@Composable
fun HomePlayerContentCourt(navController: NavHostController, courtPlayerCrossRefsList : List<CourtPlayerCrossRef>, idUser: Int){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth())
    {
        Spacer()
        CourtPlayerCrossRefList(courtsPlayerCrossRef = courtPlayerCrossRefsList)
    }
}