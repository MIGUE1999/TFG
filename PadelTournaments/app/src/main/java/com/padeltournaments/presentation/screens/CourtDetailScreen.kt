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
import androidx.compose.ui.text.toUpperCase
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

    idCourt?.let {
        CourtDetailSection(courtDetailViewModel = courtDetailViewModel,
        session = session,
        navController = navController,
        context = context,
        idCourt = it.toInt()
    )
    }
}
@Composable
fun CourtDetailSection(
    courtDetailViewModel: CourtDetailViewModel,
    session: LoginPref,
    navController: NavController,
    context: Context,
    idCourt: Int
) {
    val userId = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

    val bookCourtSuccess by courtDetailViewModel.bookCourtSuccess.collectAsState(false)


    DisposableEffect(bookCourtSuccess) {
        if (bookCourtSuccess) {
            if (courtDetailViewModel.idCourt.value != "" && userId != -1) {
                val bookedDateHour = courtDetailViewModel.date.value + " " + courtDetailViewModel.freeHour.value
                courtDetailViewModel.insertCourtPlayerCrossRef(userId, bookedDateHour)
                PaymentSucceed.bookCourtSucceed = false
            }
        }

        onDispose {
            // Limpiar el efecto y restablecer el estado de PaymentSucceed.bookCourtSucceed
        }
    }

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
                text = courtDetailViewModel.clubNameState.value + " PISTA Nº " +  courtDetailViewModel.courtNumber.value,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = courtDetailViewModel.ubication.value.uppercase(),
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
                    .padding(vertical = 16.dp)
            ) {
                CourtDateSection(courtDetailViewModel = courtDetailViewModel, idCourt)
            }

            Spacer(modifier = Modifier.height(24.dp))

            if(session.getUserDetails()[LoginPref.KEY_ROL] == Rol.player) {
                Spacer(modifier = Modifier.height(24.dp))
                PayBookButton(
                    courtDetailViewModel.bookCost.value, enabled = true
                )
            } else {
                Spacer(modifier = Modifier.height(24.dp))
                PayBookButton(
                    courtDetailViewModel.bookCost.value, enabled = false
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CourtDateSection(courtDetailViewModel: CourtDetailViewModel, idCourt: Int) {
    var isBoxSelected by remember { mutableStateOf(false) }

    val bookedDateAndHourList by courtDetailViewModel.getBookedDateAndHourByCourtId(idCourt)
        .collectAsState(emptyList())
    Surface(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
            ) {

                val hoursList = listOf(
                    "08:00", "09:00", "10:00", "11:00", "12:00",
                )
                hoursList.forEach { freeHours ->
                    val dateFree = courtDetailViewModel.date.value + " " + freeHours

                    if (!bookedDateAndHourList.contains(dateFree)) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clip(CircleShape)
                                .background(
                                    color = if (isBoxSelected && freeHours == courtDetailViewModel.freeHour.value) Color.Gray else MaterialTheme.colors.primary
                                )
                                .height(35.dp)
                                .clickable() { // Solo se puede hacer clic si no está seleccionado
                                    isBoxSelected = true
                                    courtDetailViewModel.freeHour.value = freeHours
                                }
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
            ) {

                val hoursList = listOf(
                    "13:00", "14:00", "15:00", "16:00", "17:00",
                )

                hoursList.forEach { freeHours ->
                    val dateFree = courtDetailViewModel.date.value + " " + freeHours

                    if (!bookedDateAndHourList.contains(dateFree)) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clip(CircleShape)
                                .background(
                                    color = if (isBoxSelected && freeHours == courtDetailViewModel.freeHour.value) Color.Gray else MaterialTheme.colors.primary
                                )
                                .height(35.dp)
                                .clickable() { // Solo se puede hacer clic si no está seleccionado
                                    isBoxSelected = true
                                    courtDetailViewModel.freeHour.value = freeHours
                                }
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
            ) {

                val hoursList = listOf(
                    "18:00", "19:00", "20:00", "21:00", "22:00"
                )

                hoursList.forEach { freeHours ->

                    val dateFree = courtDetailViewModel.date.value + " " + freeHours

                    if (!bookedDateAndHourList.contains(dateFree)) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clip(CircleShape)
                                .background(
                                    color = if (isBoxSelected && freeHours == courtDetailViewModel.freeHour.value) Color.Gray else MaterialTheme.colors.primary
                                )
                                .height(35.dp)
                                .clickable() { // Solo se puede hacer clic si no está seleccionado
                                    isBoxSelected = true
                                    courtDetailViewModel.freeHour.value = freeHours
                                }
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
}

@Composable
fun PayBookButton(inscriptionCost: String, enabled: Boolean) {
    val euroSymbol = '\u20AC'
    val context = LocalContext.current
    val activity = context as Activity
    var text = "RESERVAR - $inscriptionCost $euroSymbol"

    Button(onClick = {
        val razorPayments = RazorPayments(activity)
        razorPayments.startCourtPayment(inscriptionCost.toInt())
        PaymentSucceed.isBookCourt = true
    },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = MaterialTheme.shapes.medium) {
        Text(text = text)
    }
    Spacer(modifier = Modifier.height(16.dp))

}







