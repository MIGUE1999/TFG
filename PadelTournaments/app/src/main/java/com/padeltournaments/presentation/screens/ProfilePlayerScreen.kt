package com.padeltournaments.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.presentation.composables.TopProfileCard
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.SignUpViewModel
import com.padeltournaments.util.InputType
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.playerScreens

@Composable
fun ProfilePlayerScreen(session : LoginPref, navController : NavHostController) {
    Scaffold(topBar = { TopBar() },
        content = { ProfilePlayerContent(session = session, navController = navController) },
        bottomBar = { BottomBar(navController = navController, screens = playerScreens) }
    )
}

@Composable
fun ProfilePlayerContent(session : LoginPref, navController : NavHostController) {
    Column(modifier = Modifier.padding(bottom = 40.dp)) {
        TopProfileCard(session = session, navController = navController)
        ProfilePlayerData()
    }
}

@Composable
fun ProfilePlayerData(
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current

    Column(
        Modifier
            .navigationBarsWithImePadding()
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInputEditOrganizer(InputType.Name,
            keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus() }
            ),
            signUpViewModel = signUpViewModel
        )
        TextInputEditOrganizer(InputType.Surname,
            keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus() }
            ),
            signUpViewModel = signUpViewModel
        )

        TextInputEditOrganizer(
            InputType.Email, keyboardActions = KeyboardActions(onDone = {
            passwordFocusRequester.requestFocus() }),
            signUpViewModel = signUpViewModel
        )

        TextInputEditOrganizer(InputType.Telephone,
            keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus() }
            ),
            signUpViewModel = signUpViewModel
        )

        TextInputEditOrganizer(InputType.Password,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus() }
            ),
            focusRequester = passwordFocusRequester,
            signUpViewModel = signUpViewModel
        )
        
        TextInputEditOrganizer(InputType.Nickname,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus() }
            ),
            focusRequester = passwordFocusRequester,
            signUpViewModel = signUpViewModel
        )

        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Cambios", Modifier.padding(vertical = 8.dp))
        }
    }
}