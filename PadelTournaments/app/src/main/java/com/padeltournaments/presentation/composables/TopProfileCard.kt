package com.padeltournaments.presentation.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.SignUpViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.Rol
@Composable
fun TopProfileCard(session: LoginPref, navController: NavHostController, signUpViewModel: SignUpViewModel) {
    Card(
        elevation = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        var isPressed by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(10.dp)
                ) {
                    signUpViewModel.photo.value?.let {
                        RoundImage(
                            image = it,
                            modifier = Modifier
                                .fillMaxHeight(0.7f)
                            //.weight(1f)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(0.6f)
                ) {
                    if (signUpViewModel.rol.value == Rol.organizer) {
                        Text(
                            text = signUpViewModel.clubName.value,
                            modifier = Modifier
                                .padding(top = 25.dp, end = 20.dp),
                            fontSize = 25.sp
                        )
                    } else {
                        Text(
                            text = signUpViewModel.nickname.value,
                            modifier = Modifier
                                .padding(top = 25.dp, end = 20.dp),
                            fontSize = 25.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(0.1f)
                ) {
                    IconButton(
                        onClick = {
                            session.logoutUser()
                            navController.navigate(NavigationScreens.LogIn.route)
                        },
                    ) {
                        Icon(Icons.Filled.ExitToApp, null)
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {

                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(10.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "Mi Informacion",
                        textAlign = TextAlign.Center, // hacer que el texto esté centrado horizontalmente
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxHeight()
                ) {
                    IconButton(
                        onClick = {
                            isPressed = !isPressed
                            signUpViewModel.showForm.value = !signUpViewModel.showForm.value
                        }
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = null,
                            modifier = if (isPressed) Modifier.background(Color.Gray) else Modifier
                        )
                    }
                }
            }
        }
    }
}


    @Composable
    fun RoundImage(
        image: Bitmap,
        modifier: Modifier = Modifier
    ) {
        val imageBitmap = remember(image) { image.asImageBitmap() }

        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .clip(CircleShape)
        )
    }