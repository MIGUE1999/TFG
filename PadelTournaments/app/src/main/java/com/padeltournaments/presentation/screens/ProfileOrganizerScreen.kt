package com.padeltournaments.presentation.screens

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.material.R
import com.padeltournaments.presentation.composables.TopProfileCard
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.theme.Shapes
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.presentation.viewmodels.SignUpViewModel
import com.padeltournaments.util.InputType
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.organizerScreens

@Composable
fun ProfileOrganizerScreen(session : LoginPref, navController : NavHostController) {
    Scaffold(topBar = { TopBar() },
            content = {ProfileOrganizerContent(session, navController)},
            bottomBar = { BottomBar(navController = navController, screens = organizerScreens) }
    )
}

@Composable
fun ProfileOrganizerContent(session : LoginPref, navController : NavHostController) {
    Column(modifier = Modifier.padding(bottom = 40.dp)) {
        TopProfileCard(session = session, navController = navController)
        ProfileOrganizerData()
    }
}


@Composable
fun ProfileOrganizerData(
    signUpViewModel: SignUpViewModel = hiltViewModel()) {
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

            TextInputEditOrganizer(InputType.Email, keyboardActions = KeyboardActions(onDone = {
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

            TextInputEditOrganizer(InputType.Cif,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus() }
                ),
                signUpViewModel = signUpViewModel
            )

            TextInputEditOrganizer(InputType.ClubName,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus() }
                ),
                signUpViewModel = signUpViewModel
            )

            TextInputEditOrganizer(InputType.BankAccount,
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

@Composable
fun TextInputEditOrganizer(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    signUpViewModel: SignUpViewModel? = null,
){

    if(signUpViewModel != null) {
        val value: String
        when (inputType.label) {
            InputType.Name.label -> {

                value = signUpViewModel.nameUser.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onNameChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )

            }
            InputType.Surname.label -> {
                value = signUpViewModel.surnameUser.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onSurnameChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )
            }
            InputType.Telephone.label -> {
                value = signUpViewModel.tlfUser.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onTlfChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )
            }
            InputType.Email.label -> {
                value = signUpViewModel.emailUser.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onEmailChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )
            }
            InputType.Password.label -> {

                var passwordVisibility by remember { mutableStateOf(false) }

                val icon = if (passwordVisibility)
                    painterResource(id = R.drawable.design_ic_visibility)
                else
                    painterResource(id = R.drawable.design_ic_visibility_off)

                value = signUpViewModel.passwordUser.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onPasswordChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
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
            InputType.Email.label -> {
                value = signUpViewModel.emailUser.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onEmailChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )
            }
            InputType.Nickname.label -> {
                value = signUpViewModel.nickname.value
                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onNicknameChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )

            }
            InputType.BankAccount.label -> {
                value = signUpViewModel.bankAccount.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onBankAccountChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )
            }
            InputType.Cif.label -> {

                value = signUpViewModel.cif.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onCifChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )

            }
            InputType.ClubName.label -> {
                value = signUpViewModel.clubName.value

                TextField(
                    value = value,
                    onValueChange = { inputValue ->
                        signUpViewModel.onClubNameChanged(inputValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder(focusRequester ?: FocusRequester()),
                    leadingIcon = {
                        if (inputType.icon != null)
                            Icon(imageVector = inputType.icon, null)
                    },
                    label = { Text(text = inputType.label) },
                    shape = Shapes.small,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = inputType.keyboardOptions,
                    visualTransformation = inputType.visualTransformation,
                    keyboardActions = keyboardActions
                )

            }
            else -> {
                Log.d("InputText", "No coincide el textfield con ningun label")
            }
        }
    }
}

