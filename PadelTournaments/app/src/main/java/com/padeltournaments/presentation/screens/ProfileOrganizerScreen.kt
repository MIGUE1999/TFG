package com.padeltournaments.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.presentation.composables.TopProfileCard
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.util.InputType
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens

@Composable
fun ProfileOrganizerScreen(session : LoginPref, navController : NavHostController)
{
    Scaffold(topBar = { TopBar() },
        content = {ProfileOrganizerContent(session, navController)},
    bottomBar = { BottomBar(navController = navController, screens = organizerScreens) }
    )
}

@Composable
fun ProfileOrganizerContent(session : LoginPref, navController : NavHostController)
{
    Column(modifier = Modifier.padding(bottom = 40.dp)) {
        TopProfileCard(session = session, navController = navController)
        ProfileOrganizerData()
    }
}


@Composable
fun ProfileOrganizerData(
    createTournamentViewModel: CreateTournamentViewModel = hiltViewModel())
{
    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current

    ProvideWindowInsets {
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextInput(
                inputType = InputType.Name,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus() }
                ),
                createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.Surname,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus() }
                ),
                createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.Email, keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus() }),
                createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.Telephone,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus() }
                ),
                createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.Password,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus() }
                ),
                focusRequester = passwordFocusRequester,
                createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.Cif,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus() }
                ),
                createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.ClubName,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus() }
                ),
                createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.BankAccount,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus() }
                ),
                focusRequester = passwordFocusRequester,
                createTournamentViewModel = createTournamentViewModel
            )

            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Guardar Cambios", Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

