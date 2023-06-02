package com.padeltournaments.presentation.screens

import android.content.Context
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun TournamentDetailScreen(context : Context,
                           navController: NavController,
                           idTournament: String?
)
{
    if (idTournament != null) {
        Text(text = idTournament.toString())
    }
}