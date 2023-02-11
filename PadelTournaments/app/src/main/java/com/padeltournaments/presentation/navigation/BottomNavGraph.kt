package com.padeltournaments.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.padeltournaments.presentation.screens.*
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.Rol

//Es el que muestra la pantalla en el scaffold: content = {BottomNavGraph(navController = navController)},
@Composable
fun BottomNavGraph( navController : NavHostController = rememberNavController(),
                    context : Context = LocalContext.current,
)
{
    var session = LoginPref(context)

    var startDestination = if(session.isLoggedIn()) {
        if(session.getUserDetails()[LoginPref.KEY_ROL] == Rol.organizer)
            NavigationScreens.HomeOrganizer.route
        else
            NavigationScreens.HomePlayer.route
    }
    else
        NavigationScreens.LogIn.route

    NavHost(navController = navController, startDestination = startDestination){
        composable(route = NavigationScreens.LogIn.route) {
            LoginScreen(navController = navController, session = session)
        }
        composable(route = NavigationScreens.SignUp.route){
            SignUp(navController = navController, context = context )
        }
        composable(route = NavigationScreens.HomeOrganizer.route){
            HomeOrganizerScreen(session = session, navController = navController)
        }
        composable(route = NavigationScreens.HomePlayer.route){
            HomePlayerScreen(session = session, navController =  navController)
        }
        composable(route = NavigationScreens.ProfileOrganizer.route){
            ProfileOrganizerScreen(session = session, navController = navController )
        }
        composable(route = NavigationScreens.ProfilePlayer.route){
            ProfilePlayerScreen(session = session, navController = navController )
        }
        composable(route = NavigationScreens.SearchOrganizer.route){
            SearchOrganizerScreen(session = session, navController = navController )
        }
        composable(route = NavigationScreens.SearchPlayer.route){
            SearchPlayerScreen(session = session, navController = navController )
        }
        composable(route = NavigationScreens.CreateTournament.route){
            CreateTournamentScreen(context = context, session = session, navController = navController )
        }
    }
}