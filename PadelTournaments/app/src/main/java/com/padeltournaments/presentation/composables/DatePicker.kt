package com.padeltournaments.presentation.composables

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel


@Composable
fun showDatePicker(context: Context, startDate: String, endDate: String, createTournamentViewModel : CreateTournamentViewModel){

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    //val dateInit = remember { mutableStateOf("") }
    val dateEnd = remember { mutableStateOf("") }

    val datePickerDialogInit = DatePickerDialog(
        context,
        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            //dateInit.value = "$dayOfMonth/$month/$year"
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
            Text(text = "$startDate: ${createTournamentViewModel.getDateIni()}", modifier = Modifier.width(120.dp))
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

            Text(text = "$endDate: ${createTournamentViewModel.getDateEnd()}", modifier = Modifier.width(120.dp) )
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