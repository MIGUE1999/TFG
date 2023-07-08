package com.padeltournaments.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
sealed class NavigationScreens(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    object SearchOrganizer : NavigationScreens(
        route = "search_organizer",
        title = "SearchOrganizer",
        icon = Icons.Default.Search
    )
    object SearchPlayer : NavigationScreens(
        route = "search_player",
        title = "SearchPlayer",
        icon = Icons.Default.Search
    )
    object HomeOrganizer : NavigationScreens(
        route = "home_organizer",
        title = "HomeOrganizer",
        icon = Icons.Default.Home
    )
    object HomePlayer : NavigationScreens(
        route = "home_player",
        title = "HomePlayer",
        icon = Icons.Default.Home
    )
    object ProfileOrganizer : NavigationScreens(
        route = "profile_organizer",
        title = "ProfileOrganizer",
        icon = Icons.Default.Person
    )
    object ProfilePlayer : NavigationScreens(
        route = "profile_player",
        title = "ProfilePlayer",
        icon = Icons.Default.Person
    )
    object CreateTournament : NavigationScreens(
        route = "create_tournament",
        title = "CreateTournament",
    )
    object EditTournament : NavigationScreens(
        route = "edit_tournament/{idTournament}",
        title = "EditTournament",
    )
    object LogIn : NavigationScreens(
        route = "log_in",
        title = "LogIn",
    )
    object SignUp : NavigationScreens(
        route = "sign_up",
        title = "SignUp",
    )
    object TournamentDetail : NavigationScreens(
        route = "tournament_detail/{idTournament}",
        title = "TournamentDetail",
    )
    object CreateCourt : NavigationScreens(
        route = "create_court",
        title = "CreateCourt",
    )
    object CourtDetail : NavigationScreens(
        route = "court_detail/{idCourt}",
        title = "CourtDetail",
    )
    object EditCourt : NavigationScreens(
        route = "edit_court/{idCourt}",
        title = "EditTournament",
    )
}