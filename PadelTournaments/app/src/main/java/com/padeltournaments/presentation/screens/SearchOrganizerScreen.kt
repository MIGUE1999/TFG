package com.padeltournaments.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.*
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.SearchViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens
import com.padeltournaments.util.playerScreens

@Composable
fun SearchOrganizerScreen(session : LoginPref,
                          searchViewModel : SearchViewModel = hiltViewModel(),
                          navController : NavHostController) {

    val allTournaments = searchViewModel.getAllTournaments()
        .collectAsState(initial = emptyList())

    val allCourts = searchViewModel.getAllCourts()
        .collectAsState(initial = emptyList())

    val filteredList by searchViewModel.filteredList.observeAsState(emptyList())
    val filteredCourtList by searchViewModel.filteredCourtList.observeAsState(emptyList())

    Scaffold(
        content = { TabBarSearchPlayer(navController = navController, tournaments = allTournaments.value,
            filteredList = filteredList, searchViewModel = searchViewModel, isOrganizer = true, session = session,
            courts = allCourts.value.toList(), filteredCourtList = filteredCourtList) },
        bottomBar = { BottomBar(navController = navController, organizerScreens) }
        )
}
@Composable
fun SearchContent(navController: NavHostController,
                  tournaments : List<TournamentEntity>,
                  tournamentsFiltered : List<TournamentEntity>?,
                  searchViewModel: SearchViewModel,
                  isOrganizer: Boolean
) {
    val isFiltering by searchViewModel.isFiltering.observeAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth())
    {
        SearchBar(tournaments = tournaments, searchViewModel = searchViewModel)
        FilterLazyRow(searchViewModel, tournaments)
        Spacer()
        if (isFiltering == true)
            TournamentList(isOrganizer = isOrganizer, navController = navController, tournaments = tournamentsFiltered, isSearch = true)
        else TournamentList(isOrganizer = isOrganizer, navController = navController, tournaments = tournaments, isSearch = true)
    }
}

@Composable
fun TabBarSearchOrganizer(navController : NavHostController,
                       tournaments: List<TournamentEntity>,
                       courts: List<CourtEntity>,
                       session: LoginPref,
                       filteredList: List<TournamentEntity>,
                       searchViewModel : SearchViewModel,
                       isOrganizer : Boolean,
                       filteredCourtList: List<CourtEntity>
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
                        text = "TORNEOS",
                        modifier = Modifier.weight(1f)
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = "PISTAS",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        // Aquí puedes agregar el contenido correspondiente a cada pestaña
        val idUser = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

        if (selectedTabIndex == 0) {
            SearchContent(navController = navController, tournaments, filteredList, searchViewModel, isOrganizer = isOrganizer)
        } else {
            SearchCourt(
                navController = navController,
                courts = courts,
                courtsFiltered = filteredCourtList,
                searchViewModel = searchViewModel,
                isOrganizer = false,
                idUser = idUser
            )
        }

    }

}