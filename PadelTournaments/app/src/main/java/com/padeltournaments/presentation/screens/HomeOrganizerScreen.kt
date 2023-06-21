package com.padeltournaments.presentation.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.CourtList
import com.padeltournaments.presentation.composables.Spacer
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.FAB
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens
import com.padeltournaments.presentation.composables.TournamentList
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel


@Composable
fun Tab(selected: Boolean, onClick: () -> Unit, text: String, modifier: Modifier) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colors.primary else Color.Transparent
    )

    val textColor by animateColorAsState(
        targetValue = if (selected) Color.White else MaterialTheme.colors.primary
    )

        Box(
            modifier = modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickable { onClick() }
                .background(backgroundColor),
            contentAlignment = Alignment.Center,
            ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(8.dp)
            )
        }
}

@Composable
fun TabBar(navController : NavHostController,
            tournaments: List<TournamentEntity>,
            courts: List<CourtEntity>,
            homeOrganizerViewModel: HomeOrganizerViewModel,
            session: LoginPref) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                ,
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
                        text = "Tus torneos",
                        modifier = Modifier.weight(1f)
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = "Tus pistas",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        // Aquí puedes agregar el contenido correspondiente a cada pestaña
        if (selectedTabIndex == 0) {
            homeOrganizerViewModel.isTournamentList.value = true
            HomeOrganizerContent(navController = navController, tournaments)
        } else {
            homeOrganizerViewModel.isTournamentList.value = false
            HomeOrganizerContentCourt(navController = navController, courts = courts, session = session)
        }
    }
}
@Composable
fun HomeOrganizerScreen(session : LoginPref,
                        homeOrganizerViewModel : HomeOrganizerViewModel = hiltViewModel(),
                        createCourtViewModel: CreateCourtViewModel = hiltViewModel(),
                        navController : NavHostController)
{
    val userId = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

    homeOrganizerViewModel.setUserId(userId)
    createCourtViewModel.setUserId(userId)

    val tournaments by homeOrganizerViewModel.tournamentsByUserId.collectAsState(emptyList())
    val courts by createCourtViewModel.courtsByUserId.collectAsState(emptyList())

    Scaffold(bottomBar = { BottomBar(navController = navController, organizerScreens) },
             content = {
                     TabBar(navController = navController, tournaments = tournaments, courts = courts, homeOrganizerViewModel = homeOrganizerViewModel, session = session)
             },
             floatingActionButton = { FAB(navController = navController, isListTournaments = homeOrganizerViewModel.isTournamentList.value) }
        )
}
@Composable
fun HomeOrganizerContent(navController: NavHostController, tournaments : List<TournamentEntity>){
    Column(horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth())
    {
        Spacer()
        TournamentList(isOrganizer = true, navController = navController, tournaments = tournaments)
    }
}

@Composable
fun HomeOrganizerContentCourt(navController: NavHostController, courts : List<CourtEntity>, session: LoginPref){
    Column(horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth())
    {
        Spacer()
        CourtList(isOrganizer = true, navController = navController, courts = courts, session = session)
    }
}





