package com.padeltournaments.presentation.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.data.entities.UserEntity
import com.padeltournaments.presentation.composables.CustomTextInput
import com.padeltournaments.presentation.composables.PickImageFromGalleryRoundedImage
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.SignUpViewModel
import com.padeltournaments.util.Rol
@Composable
fun SignUp(navController : NavController,
           signUpViewModel : SignUpViewModel = hiltViewModel(),
           context: Context
)
{
    val focusManager = LocalFocusManager.current
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

        PickImageFromGalleryRoundedImage(signUpViewModel)
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
            )
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
            )
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
            )
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
            )
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
            )
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
            )
        )

        Text(text = "Seleccione su rol", modifier = Modifier.padding(0.dp))

        Row {
            RadioButton(
                selected = signUpViewModel.rol.value == Rol.player,
                onClick = { signUpViewModel.rol.value = Rol.player }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(Rol.player)
            Spacer(modifier = Modifier.size(16.dp))

            RadioButton(
                selected = signUpViewModel.rol.value == Rol.organizer,
                onClick = { signUpViewModel.rol.value = Rol.organizer },
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(Rol.organizer)
        }

        if(signUpViewModel.rol.value == Rol.organizer){
            CustomTextInput(
                value = signUpViewModel.cif.value,
                onValueChange = { signUpViewModel.onCifChanged(it) },
                label = "CIF",
                showError = !signUpViewModel.validateCif.value,
                errorMessage = signUpViewModel.validateCifError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )
            CustomTextInput(
                value = signUpViewModel.clubName.value,
                onValueChange = { signUpViewModel.onClubNameChanged(it) },
                label = "Nombre del Club",
                showError = !signUpViewModel.validateClubName.value,
                errorMessage = signUpViewModel.validateClubNameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )
            CustomTextInput(
                value = signUpViewModel.bankAccount.value,
                onValueChange = { signUpViewModel.onBankAccountChanged(it) },
                label = "Cuenta Bancaria",
                showError = !signUpViewModel.validateBankAccount.value,
                errorMessage = signUpViewModel.validateBankAccountError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
        }

        if(signUpViewModel.rol.value == Rol.player){
            CustomTextInput(
                value = signUpViewModel.nickname.value,
                onValueChange = { signUpViewModel.onNicknameChanged(it) },
                label = "Nickname",
                showError = !signUpViewModel.validateNickname.value,
                errorMessage = signUpViewModel.validateNicknameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
        }

        Button(onClick = {
            if(signUpViewModel.validateData()) {
                when (signUpViewModel.rol.value) {
                    Rol.player -> {
                        val usr = UserEntity(
                            name = signUpViewModel.nameUser.value,
                            surname = signUpViewModel.surnameUser.value,
                            telephoneNumber = signUpViewModel.tlfUser.value,
                            email = signUpViewModel.emailUser.value,
                            password = signUpViewModel.passwordUser.value,
                            rol = Rol.player,
                            photo = signUpViewModel.photo.value
                        )

                        signUpViewModel.insertPlayerByUser(usr)
                        navController.navigate(NavigationScreens.LogIn.route)
                    }
                    Rol.organizer -> {
                        val usr = UserEntity(
                            name = signUpViewModel.nameUser.value,
                            surname = signUpViewModel.surnameUser.value,
                            telephoneNumber = signUpViewModel.tlfUser.value,
                            email = signUpViewModel.emailUser.value,
                            password = signUpViewModel.passwordUser.value,
                            rol = Rol.organizer,
                            photo = signUpViewModel.photo.value
                        )

                        signUpViewModel.insertOrganizerByUser(usr)
                        navController.navigate(NavigationScreens.LogIn.route)
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "Error al registrarse: Escoge un Rol",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        },
            modifier = Modifier.fillMaxWidth()) {
            Text("REGISTRATE", Modifier.padding(vertical = 8.dp))
        }
    }
}