package com.padeltournaments.presentation.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.padeltournaments.data.entities.UserEntity

@Composable
fun ProfilesBookedLazyRow(
    profiles: List<UserEntity>
) {
    LazyRow(modifier = Modifier.fillMaxWidth().height(150.dp)) {
        items(items = profiles) { profile ->
            Column(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                profile.photo?.let {
                    RoundImage(
                        image = it,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .weight(1f)
                    )
                }
                Text(
                    text = profile.name.uppercase()+ "\n" + profile.surname.uppercase(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}



