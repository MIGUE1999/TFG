package com.padeltournaments.presentation.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.presentation.composables.CustomTextInput
import com.padeltournaments.presentation.composables.TopProfileCard
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.SignUpViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.playerScreens
@Composable
fun ProfilePlayerScreen(session : LoginPref, navController : NavHostController, signUpViewModel: SignUpViewModel = hiltViewModel()) {
    val userId = session.getUserDetails()[LoginPref.KEY_ID]
    val handler = Handler(Looper.getMainLooper())
    val delayMillis = 5

    val runnable = Runnable {
        if (userId != null) {
            signUpViewModel.refreshTextFieldValues(userId, isOrganizer = false)
        }
    }
    handler.postDelayed(runnable, delayMillis.toLong())

    Scaffold(topBar = { TopBar() },
        content = { ProfilePlayerContent(session = session, navController = navController, signUpViewModel) },
        bottomBar = { BottomBar(navController = navController, screens = playerScreens) }
    )
}
@Composable
fun ProfilePlayerContent(session : LoginPref, navController : NavHostController, signUpViewModel: SignUpViewModel = hiltViewModel()) {
    Column(modifier = Modifier.padding(bottom = 40.dp)) {
        TopProfileCard(session = session, navController = navController, signUpViewModel = signUpViewModel)
        ProfilePlayerData(signUpViewModel, session)
    }
}
@Composable
fun ProfilePlayerData(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    session: LoginPref
) {
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

        CustomTextInput(
            value = signUpViewModel.nameUser.value,
            onValueChange = {signUpViewModel.onNameChanged(it) },
            label = "Name",
            showError = !signUpViewModel.validateName.value,
            errorMessage = signUpViewModel.validateNameError,
            leadingIconImageVector = Icons.Default.Person,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            enabled = signUpViewModel.showForm.value
        )
        CustomTextInput(
            value = signUpViewModel.surnameUser.value,
            onValueChange = { signUpViewModel.onSurnameChanged(it) },
            label = "Surname",
            showError = !signUpViewModel.validateSurname.value,
            errorMessage = signUpViewModel.validateSurnameError,
            leadingIconImageVector = Icons.Default.Person,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            enabled = signUpViewModel.showForm.value
        )
        CustomTextInput(
            value = signUpViewModel.emailUser.value,
            onValueChange = { signUpViewModel.onEmailChanged(it) },
            label = "Email",
            showError = !signUpViewModel.validateEmail.value,
            errorMessage = signUpViewModel.validateEmailError,
            leadingIconImageVector = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            enabled = signUpViewModel.showForm.value
        )
        CustomTextInput(
            value = signUpViewModel.tlfUser.value,
            onValueChange = {signUpViewModel.onTlfChanged(it) },
            label = "Numero Telefono",
            showError = !signUpViewModel.validatePhone.value,
            errorMessage = signUpViewModel.validatePhoneError,
            leadingIconImageVector = Icons.Default.Phone,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            enabled = signUpViewModel.showForm.value
        )
        CustomTextInput(
            value = signUpViewModel.passwordUser.value,
            onValueChange = {signUpViewModel.onPasswordChanged(it) },
            label = "Contraseña",
            showError = !signUpViewModel.validatePassword.value,
            errorMessage = signUpViewModel.validatePasswordError,
            isPasswordField = true,
            isPasswordVisible = signUpViewModel.isPasswordVisible.value,
            onVisibilityChange = { signUpViewModel.isPasswordVisible.value = it},
            leadingIconImageVector = Icons.Default.Lock,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            enabled = signUpViewModel.showForm.value
        )
        CustomTextInput(
            value = signUpViewModel.confirmPasswordUser.value,
            onValueChange = { signUpViewModel.onConfirmPasswordChanged(it) },
            label = "Repite Contraseña",
            showError = !signUpViewModel.validatePasswordsEqual.value,
            errorMessage = signUpViewModel.validateEqualPassowrdError,
            isPasswordField = true,
            isPasswordVisible = signUpViewModel.isConfirmPasswordVisible.value,
            onVisibilityChange = { signUpViewModel.isConfirmPasswordVisible.value = it},
            leadingIconImageVector = Icons.Default.Lock,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            enabled = signUpViewModel.showForm.value
        )

        CustomTextInput(
            value = signUpViewModel.nickname.value,
            onValueChange = { signUpViewModel.onNicknameChanged(it) },
            label = "Nickname",
            showError = !signUpViewModel.validateNickname.value,
            errorMessage = signUpViewModel.validateNicknameError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            enabled = signUpViewModel.showForm.value
        )

        val userId = session.getUserDetails()[LoginPref.KEY_ID]

        Button(onClick = {
            if (userId != null) {
            if(signUpViewModel.validateData()) {
                signUpViewModel.updatePlayer(userId )
            }
        }}, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Cambios", Modifier.padding(vertical = 8.dp))
        }
    }
}