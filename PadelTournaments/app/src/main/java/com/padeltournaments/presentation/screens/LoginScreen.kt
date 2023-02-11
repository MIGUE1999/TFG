package com.padeltournaments.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.theme.Shapes
import com.padeltournaments.presentation.viewmodels.LoginViewModel
import com.padeltournaments.util.InputType
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.Rol

@Composable
fun LoginScreen(loginViewModel : LoginViewModel = hiltViewModel(),
                navController: NavController,
                session:LoginPref
)
{
    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current
    val lifecycleOwner = LocalLifecycleOwner.current

    loginViewModel.userLogged.observe(lifecycleOwner) { user ->
        if(user != null) {
            loginViewModel.setSession(user, session)
            if(user.rol == Rol.organizer){
                navController.navigate(NavigationScreens.HomeOrganizer.route)
            }
            else{
                navController.navigate(NavigationScreens.HomePlayer.route)
            }
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

        TextInputLogin(InputType.Email, keyboardActions = KeyboardActions(onNext = {
            passwordFocusRequester.requestFocus()
        }), loginViewModel = loginViewModel)

        TextInputLogin(InputType.Password, keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }), focusRequester = passwordFocusRequester,
            loginViewModel = loginViewModel
        )

        Button(onClick = {
            loginViewModel.checkLoginCredentials()
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

@Composable
fun TextInputLogin(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    loginViewModel: LoginViewModel
) {

    val value : String

    if (inputType != InputType.Password ) {
        value = loginViewModel.emailUser.value
        TextField(
            value = value,
            onValueChange = { inputValue ->
                loginViewModel.onEmailChanged(inputValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusOrder(focusRequester ?: FocusRequester()),
            leadingIcon = {
                if (inputType.icon != null)
                    Icon(imageVector = inputType.icon, null)
            },
            label = { Text(text = inputType.label) },
            singleLine = true,
            shape = Shapes.small,
            keyboardOptions = inputType.keyboardOptions,
            visualTransformation = inputType.visualTransformation,
            keyboardActions = keyboardActions
        )
    } else {
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility)
            painterResource(id = com.google.android.material.R.drawable.design_ic_visibility)
        else
            painterResource(id = com.google.android.material.R.drawable.design_ic_visibility_off)

        value = loginViewModel.passwordUser.value
        TextField(
            value = value,
            onValueChange = {inputValue ->
                loginViewModel.onPasswordChanged(inputValue) },
            modifier = Modifier
                .fillMaxWidth()
                .focusOrder(focusRequester ?: FocusRequester()),
            leadingIcon = {
                if (inputType.icon != null)
                    Icon(imageVector = inputType.icon, null)
            },
            label = { Text(text = inputType.label) },
            singleLine = true,
            shape = Shapes.small,
            keyboardOptions = inputType.keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(painter = icon, contentDescription = "Visibility Icon")
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )
    }
}
