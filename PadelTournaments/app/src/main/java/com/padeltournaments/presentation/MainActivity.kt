package com.padeltournaments.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.padeltournaments.R
import com.padeltournaments.presentation.navigation.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                window.navigationBarColor = resources.getColor(R.color.purple_500)
                BottomNavGraph()

            }
        }
    }
}



