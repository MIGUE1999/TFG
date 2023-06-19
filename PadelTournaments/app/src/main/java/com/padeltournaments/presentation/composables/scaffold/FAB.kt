package com.padeltournaments.presentation.composables.scaffold

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.padeltournaments.presentation.navigation.NavigationScreens
@Composable
fun FAB(navController: NavHostController, isListTournaments: Boolean){
    FloatingActionButton(
        onClick = {
            if(isListTournaments) {
                navController.navigate(NavigationScreens.CreateTournament.route)
            }
            else {
                navController.navigate(NavigationScreens.CreateCourt.route)
            }
        }
    ){
        Icon(imageVector = Icons.Default.Add, "Crear Torneo/Pista")
    }
}