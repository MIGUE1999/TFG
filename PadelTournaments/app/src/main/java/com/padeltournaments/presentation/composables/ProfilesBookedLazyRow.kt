package com.padeltournaments.presentation.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
    LazyRow {
        items(items = profiles) { profile ->
            Column(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircleProfileImage("url")
                Text(
                    text = profile.name,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CircleProfileImage(imageUrl: String) {
    Icon(
        imageVector = Icons.Default.Person,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(40.dp)
    )
}