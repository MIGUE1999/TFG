package com.padeltournaments.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun Spacer(size: Int = 8) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(size.dp))