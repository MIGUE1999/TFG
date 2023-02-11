package com.padeltournaments.presentation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.SignUpViewModel
import com.padeltournaments.util.InputType
import com.padeltournaments.presentation.theme.Shapes
import com.padeltournaments.util.Rol


@Composable
fun SignUp(navController : NavController,
           signUpViewModel : SignUpViewModel = hiltViewModel(),
           context: Context
)
{
    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current

    val selectedRol = remember{
        signUpViewModel.rol
    }

        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(top = 5.dp)
            )

            TextInputSignUp(
                inputType = InputType.Name,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus()
                }), signUpViewModel = signUpViewModel
            )

            TextInputSignUp(InputType.Surname, keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus()
            }), signUpViewModel = signUpViewModel)

            TextInputSignUp(InputType.Telephone, keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus()
            }), signUpViewModel = signUpViewModel)

            TextInputSignUp(InputType.Email, keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus()
            }), signUpViewModel = signUpViewModel)

            TextInputSignUp(InputType.Password, keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }), focusRequester = passwordFocusRequester, signUpViewModel = signUpViewModel)

            Text(text = "Seleccione su rol", modifier = Modifier.padding(0.dp))

            Row {
                RadioButton(
                    selected = selectedRol.value == Rol.player,
                    onClick = { selectedRol.value = Rol.player }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Rol.player)
                Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = selectedRol.value == Rol.organizer,
                    onClick = { selectedRol.value = Rol.organizer },
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Rol.organizer)

            }

            if(selectedRol.value == Rol.organizer){
                TextInputSignUp(InputType.Cif, keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus()
                }),signUpViewModel = signUpViewModel)
                TextInputSignUp(InputType.ClubName, keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus()
                }),signUpViewModel = signUpViewModel)
                TextInputSignUp(InputType.BankAccount, keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }), focusRequester = passwordFocusRequester,
                    signUpViewModel = signUpViewModel)
            }

            if(selectedRol.value == Rol.player){
                TextInputSignUp(InputType.Nickname, keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }), focusRequester = passwordFocusRequester,
                    signUpViewModel = signUpViewModel)
            }
            Button(onClick = {
                when (selectedRol.value) {
                    Rol.player -> {
                        var usr = UserEntity(
                            name = signUpViewModel.nameUser.value,
                            surname = signUpViewModel.surnameUser.value,
                            telephoneNumber = signUpViewModel.tlfUser.value,
                            email = signUpViewModel.emailUser.value,
                            password = signUpViewModel.passwordUser.value,
                            rol = Rol.player
                        )

                        signUpViewModel.insertPlayerByUser(usr)
                        navController.navigate(NavigationScreens.LogIn.route)

                    }
                    Rol.organizer -> {
                        var usr = UserEntity(
                            name = signUpViewModel.nameUser.value,
                            surname = signUpViewModel.surnameUser.value,
                            telephoneNumber = signUpViewModel.tlfUser.value,
                            email = signUpViewModel.emailUser.value,
                            password = signUpViewModel.passwordUser.value,
                            rol = Rol.organizer)

                        signUpViewModel.insertOrganizerByUser(usr)
                        navController.navigate(NavigationScreens.LogIn.route)
                    }
                    else -> {
                        Toast.makeText(context, "Error al registrarse: Escoge un Rol", Toast.LENGTH_SHORT).show()
                    }
                }
            },
                modifier = Modifier.fillMaxWidth()) {
                Text("REGISTRATE", Modifier.padding(vertical = 8.dp))
            }
        }

}


@Composable
fun TextInputSignUp(
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
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
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
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
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
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
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
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
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
                    painterResource(id = com.google.android.material.R.drawable.design_ic_visibility)
                else
                    painterResource(id = com.google.android.material.R.drawable.design_ic_visibility_off)

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
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
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
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
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
                            backgroundColor = White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
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
                            backgroundColor = White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
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
                            backgroundColor = White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
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
                            backgroundColor = White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
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