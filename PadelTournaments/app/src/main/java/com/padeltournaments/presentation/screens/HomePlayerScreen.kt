package com.padeltournaments.presentation.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens
import com.padeltournaments.util.playerScreens

@Composable
fun HomePlayerScreen(session : LoginPref, navController : NavHostController) {
    Scaffold(topBar = { TopBar() },
        bottomBar = { BottomBar(navController = navController, playerScreens) },
    ) {}
}