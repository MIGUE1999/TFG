package com.padeltournaments.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.*
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.SearchViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens
@Composable
fun SearchOrganizerScreen(session : LoginPref,
                          searchViewModel : SearchViewModel = hiltViewModel(),
                          navController : NavHostController) {

    val allTournaments = searchViewModel.getAllTournaments()
        .collectAsState(initial = emptyList())

    val filteredList by searchViewModel.filteredList.observeAsState(emptyList())

    Scaffold(topBar = { TopBar()},
        bottomBar = { BottomBar(navController = navController, organizerScreens) },
        content = { SearchContent(navController = navController, allTournaments.value, filteredList, searchViewModel) },
    )
}
@Composable
fun SearchContent(navController: NavHostController,
                  tournaments : List<TournamentEntity>,
                  tournamentsFiltered : List<TournamentEntity>?,
                  searchViewModel: SearchViewModel
) {
    val isFiltering by searchViewModel.isFiltering.observeAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth())
    {
        SearchBar(tournaments = tournaments, searchViewModel = searchViewModel)
        FilterLazyRow()
        Spacer()
        if (isFiltering == true)
            TournamentList(isOrganizer = true, navController = navController, tournaments = tournamentsFiltered)
        else TournamentList(isOrganizer = true, navController = navController, tournaments = tournaments)
    }
}