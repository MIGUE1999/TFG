package com.padeltournaments.presentation.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.padeltournaments.R
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.util.LoginPref


@Composable
fun TopProfileCard(session: LoginPref, navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .height(200.dp)

    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(modifier = Modifier.background(Color.Yellow)) {
                RoundImage(
                    image = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .background(Color.Blue)
                )

                Spacer(modifier = Modifier.size(10.dp))




                Text(
                    "Nombre Usuario", modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxHeight(0.7f)
                )
                Spacer(modifier = Modifier.size(10.dp))

                IconButton(
                    onClick = {
                        Log.d("PROFILE SCREEN", "Id: " + session.getUserDetails().get(LoginPref.KEY_ID))
                        session.logoutUser()
                        navController.navigate(NavigationScreens.LogIn.route)
                    },
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .background(Color.Red),

                    ) {
                    Icon(Icons.Filled.ExitToApp, null)
                }
            }

            Spacer(10)

            Button(onClick = { }) {
                Text(
                    "Modificar Datos", textAlign = TextAlign.Center, // make text center horizontal
                    modifier = Modifier
                        .wrapContentHeight()
                )
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
