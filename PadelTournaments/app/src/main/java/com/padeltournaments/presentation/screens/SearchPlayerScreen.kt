package com.padeltournaments.presentation.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.SearchViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.playerScreens

@Composable
fun SearchPlayerScreen(session : LoginPref,
                          searchViewModel : SearchViewModel = hiltViewModel(),
                          navController : NavHostController) {

    val allTournaments = searchViewModel.getAllTournaments()
        .collectAsState(initial = emptyList())

    val filteredList by searchViewModel.filteredList.observeAsState(emptyList())

    Scaffold(topBar = { TopBar()},
        bottomBar = { BottomBar(navController = navController, playerScreens) },
        content = { SearchContent(navController = navController, allTournaments.value, filteredList, searchViewModel, isOrganizer = false) },
    )
}