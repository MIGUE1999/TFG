package com.padeltournaments.presentation.composables

import android.util.Log
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.padeltournaments.R
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.SignUpViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.Rol
@Composable
fun TopProfileCard(session: LoginPref, navController: NavHostController, signUpViewModel: SignUpViewModel) {
    Card(
        elevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        var isPressed by remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .border(width = 1.5.dp, color = Color.Black)) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.5.dp, color = Color.Black)) {

                Spacer(modifier = Modifier.size(10.dp))
                RoundImage(
                    image = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .weight(1f)
                        .background(color = Color.Yellow)
                )

                Spacer(modifier = Modifier.size(10.dp))

                Box(
                    Modifier
                        .fillMaxHeight(0.7f)
                        .weight(1f)
                        .background(color = Color.Blue)
                ) {
                    if (signUpViewModel.rol.value == Rol.organizer) {
                        Text(
                            text = signUpViewModel.clubName.value,
                            modifier = Modifier
                                .padding(top = 25.dp, end = 20.dp),
                            fontSize = 25.sp
                        )
                    }
                    else {
                        Text(
                            text = signUpViewModel.nickname.value,
                            modifier = Modifier
                                .padding(top = 25.dp, end = 20.dp),
                            fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))

                Box(
                    Modifier
                        .fillMaxHeight(0.7f)
                        .weight(1f)
                        .background(color = Color.Red),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    IconButton(
                        onClick = {
                            Log.d(
                                "PROFILE SCREEN",
                                "Id: " + session.getUserDetails().get(LoginPref.KEY_ID)
                            )
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
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "Mi Informacion",
                        textAlign = TextAlign.Center, // make text center horizontal
                    )
                }

                val border = if (isPressed){
                    1.5.dp
                } else {
                    0.dp
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .border(width = border, color = Color.Black)
                ) {
                    IconButton(
                        onClick = {
                            isPressed = !isPressed
                            //funcion que permita escribir en los textfields de ProfileOrganizerData
                            signUpViewModel.showForm.value = !signUpViewModel.showForm.value
                        }) {
                        Icon(Icons.Filled.Edit, null)
                    }
                }
            }
        }
    }
}
@Composable
fun RoundImage(
    image : Painter,
    modifier : Modifier = Modifier
){
    Image(painter = image,
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
