package com.padeltournaments.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.presentation.composables.CustomTextInput
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.LoginViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.Rol
@Composable
fun LoginScreen(loginViewModel : LoginViewModel = hiltViewModel(),
                navController: NavController,
                session:LoginPref
)
{
    val focusManager: FocusManager = LocalFocusManager.current

    fun onCheckUserFinish(user: UserEntity) {
            loginViewModel.setSession(user, session)
            if(user.rol == Rol.organizer){
                navController.navigate(NavigationScreens.HomeOrganizer.route)
            }
            else{
                    navController.navigate(NavigationScreens.HomePlayer.route)
                }
    }

    Column(
        Modifier
            .navigationBarsWithImePadding()
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text="Padel Tournaments",
            color = White,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier=Modifier.padding(bottom = 30.dp) )

        CustomTextInput(
            value = loginViewModel.emailUser.value,
            onValueChange = {loginViewModel.onEmailChanged(it) },
            label = "Email",
            showError = !loginViewModel.validateEmail.value,
            errorMessage = loginViewModel.validateEmailError,
            leadingIconImageVector = Icons.Default.Person,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            )
        )

        CustomTextInput(
            value = loginViewModel.passwordUser.value,
            onValueChange = {loginViewModel.onPasswordChanged(it) },
            label = "Contraseña",
            showError = !loginViewModel.validatePassword.value,
            errorMessage = loginViewModel.validatePasswordError,
            isPasswordField = true,
            isPasswordVisible = loginViewModel.isPasswordVisible.value,
            onVisibilityChange = { loginViewModel.isPasswordVisible.value = it},
            leadingIconImageVector = Icons.Default.Lock,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            )
        )

        Button(onClick = {
            if(loginViewModel.validateData()) {
                loginViewModel.checkLoginCredentials{ user ->
                    onCheckUserFinish(user)
                }
            }
        },
            modifier = Modifier.fillMaxWidth()) {
            Text("Iniciar Sesion", Modifier.padding(vertical = 8.dp))
        }
        Divider(
            color = White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("No tienes cuenta?")
            TextButton(onClick = {
                navController.navigate(NavigationScreens.SignUp.route)
                 }) {
                Text("Registrate")
            }
        }
    }
}
