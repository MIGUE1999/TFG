package com.padeltournaments.presentation.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.playerScreens

@Composable
fun SearchPlayerScreen(session : LoginPref, navController : NavHostController) {
    Scaffold(topBar = { TopBar() },
        bottomBar = { BottomBar(navController = navController, playerScreens) },
    ) {}
}