package com.padeltournaments.presentation.screens

import android.app.Activity
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.padeltournaments.presentation.composables.showDatePicker
import com.padeltournaments.presentation.composables.showDatePickerCourt
import com.padeltournaments.presentation.viewmodels.CourtDetailViewModel
import com.padeltournaments.presentation.viewmodels.RazorPayments
import com.padeltournaments.util.*

@Composable
fun CourtDetailScreen( context : Context,
                            navController: NavController,
                            idCourt: String?,
                       courtDetailViewModel: CourtDetailViewModel = hiltViewModel(),
                            session: LoginPref,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Green)
    ) {

        CourtDetailScreenContent(
            context,
            navController,
            idCourt,
            courtDetailViewModel,
            session,
        )

    }
}

@Composable
fun CourtDetailTopSection(
    navController: NavController,
) {
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(36.dp)
                .offset(10.dp, 16.dp)
                .zIndex(1f)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun CourtDetailScreenContent(context : Context,
                                  navController: NavController,
                                  idCourt: String?,
                                  courtDetailViewModel: CourtDetailViewModel = hiltViewModel(),
                                  session: LoginPref,
)
{
    LaunchedEffect(idCourt) {
        if (idCourt != null) {
            courtDetailViewModel.setCourtById(idCourt.toInt())
        }
    }

    CourtDetailSection(courtDetailViewModel = courtDetailViewModel,
        session = session,
        navController = navController,
        context = context
    )
}
@Composable
fun CourtDetailSection(
    courtDetailViewModel: CourtDetailViewModel,
    session: LoginPref,
    navController: NavController,
    context: Context
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        ) {
            CourtDetailTopSection(
                navController = navController,
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = courtDetailViewModel.clubNameState.value + " pista Nº " +  courtDetailViewModel.courtNumber.value,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = courtDetailViewModel.ubication.value,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(24.dp))

            showDatePickerCourt(context,
                BOOK_COURT_DATE,
                courtDetailViewModel = courtDetailViewModel,
                showError = !courtDetailViewModel.validateDate.value,
                errorMessage = courtDetailViewModel.validateDateError,
            )

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .background(color = Color.Red)
                    .padding(vertical = 16.dp)
            ) {
                CourtDateSection(courtDetailViewModel = courtDetailViewModel)
            }

            Spacer(modifier = Modifier.height(24.dp))

            if(session.getUserDetails()[LoginPref.KEY_ROL] == Rol.player) {
                Spacer(modifier = Modifier.height(24.dp))
                PayBookButton(
                    courtDetailViewModel.bookCost.value
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CourtDateSection(courtDetailViewModel: CourtDetailViewModel) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Red)
        ) {

            val hoursList = listOf(
                "08:00", "09:00", "10:00", "11:00", "12:00",
            )

            hoursList.forEach { freeHours ->
                if (!courtDetailViewModel.reservedHours.value.contains(freeHours)) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colors.primary)
                            .height(35.dp)
                    ) {
                        Text(
                            text = freeHours,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }



        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Red)
        ) {

            val hoursList = listOf(
                "13:00", "14:00", "15:00", "16:00", "17:00",
            )

            hoursList.forEach { freeHours ->
                if (!courtDetailViewModel.reservedHours.value.contains(freeHours)) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colors.primary)
                            .height(35.dp)
                    ) {
                        Text(
                            text = freeHours,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Red)
        ) {

            val hoursList = listOf(
                "18:00", "19:00", "20:00", "21:00", "22:00"
            )

            hoursList.forEach { freeHours ->
                if (!courtDetailViewModel.reservedHours.value.contains(freeHours)) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colors.primary)
                            .height(35.dp)
                    ) {
                        Text(
                            text = freeHours,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PayBookButton(inscriptionCost: String) {
    val euroSymbol = '\u20AC'
    val context = LocalContext.current
    val activity = context as Activity
    var text = "RESERVAR - $inscriptionCost $euroSymbol"

    Button(onClick = {
        val razorPayments = RazorPayments(activity)
        razorPayments.startPayment(inscriptionCost.toInt())
    },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = MaterialTheme.shapes.medium) {
        Text(text = text)
    }
    Spacer(modifier = Modifier.height(16.dp))

}







