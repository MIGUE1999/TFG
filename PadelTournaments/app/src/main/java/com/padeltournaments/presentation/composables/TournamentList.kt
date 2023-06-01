package com.padeltournaments.presentation.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.TournamentEntity
@Composable
fun TournamentList(navController: NavHostController,
                   isOrganizer : Boolean,
                   tournaments : List<TournamentEntity>?
) {
    if (tournaments != null)
    {
        if (isOrganizer) {
            LazyColumn{
                items(items = tournaments) { tournament ->
                    TournamentCard(
                        isOrganizer = true,
                        tournament,
                        navController
                    )
                }
            }
        } else {
            LazyColumn{
                items(items = tournaments) { tournament ->
                    TournamentCard(isOrganizer = false, tournament, navController)
                }
            }
        }
    }
}
