package com.padeltournaments.presentation.composables

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import com.padeltournaments.presentation.viewmodels.CourtDetailViewModel
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
@Composable
fun showDatePicker(context: Context,
                   startDate: String,
                   endDate: String,
                   createTournamentViewModel : CreateTournamentViewModel,
                   showError: Boolean = false,
                   errorMessage: String = ""){

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    if (showError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .offset(y = (-8).dp)
                    .fillMaxWidth(0.9f)
            )
    }

    val datePickerDialogInit = DatePickerDialog(
        context,
        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            createTournamentViewModel.onDateInitChanged("$dayOfMonth/$month/$year")
        }, year, month, day
    )

    val datePickerDialogEnd = DatePickerDialog(
        context,
        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            createTournamentViewModel.onDateFinChanged("$dayOfMonth/$month/$year")
        }, year, month, day
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$startDate: ${createTournamentViewModel.dateIni.value}", modifier = Modifier.width(120.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {
                    datePickerDialogInit.show()
                }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray
                )
            ) {
                Icon(Icons.Filled.DateRange, "Date")
            }
        }
    }
    Spacer(modifier = Modifier.size(16.dp))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = "$endDate: ${createTournamentViewModel.dateEnd.value}", modifier = Modifier.width(120.dp) )
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {
                    datePickerDialogEnd.show()
                }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray
                )
            ) {
                Icon(Icons.Filled.DateRange, "Date")
            }
        }
    }
}


@Composable
fun showDatePickerCourt(context: Context,
                        startDate: String,
                        courtDetailViewModel: CourtDetailViewModel,
                        showError: Boolean = false,
                        errorMessage: String = ""){

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    if (showError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(start = 8.dp)
                .offset(y = (-8).dp)
                .fillMaxWidth(0.9f)
        )
    }

    val datePickerDialogInit = DatePickerDialog(
        context,
        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            courtDetailViewModel.onDateChanged("$dayOfMonth/$month/$year")
        }, year, month, day
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$startDate: ${courtDetailViewModel.date.value}", modifier = Modifier.width(120.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {
                    datePickerDialogInit.show()
                }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray
                )
            ) {
                Icon(Icons.Filled.DateRange, "Date")
            }
        }
    }

}