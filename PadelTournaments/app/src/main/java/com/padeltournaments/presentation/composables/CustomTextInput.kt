package com.padeltournaments.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.android.material.R
import com.padeltournaments.presentation.theme.Shapes

@Composable
fun CustomTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    leadingIconImageVector: ImageVector? = null,
    leadingIconDescription: String = "",
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String = "",
    enabled: Boolean = true
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(label) },
            leadingIcon = {
                if (leadingIconImageVector != null) {
                    Icon(
                        imageVector = leadingIconImageVector,
                        contentDescription = leadingIconDescription,
                        tint = if (showError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
                    )
                }
            },
            isError = showError,
            trailingIcon = {
                if (showError && !isPasswordField) Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Show error icon"
                )
                if (isPasswordField) {
                    IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {

                        val visibilityIcon = painterResource(id = R.drawable.design_ic_visibility)
                        val noVisibilityIcon = painterResource(id = R.drawable.design_ic_visibility_off)

                        Icon(painter = if (isPasswordVisible) visibilityIcon else noVisibilityIcon, contentDescription = "Visibility Icon")
                    }
                }
            },
            shape = Shapes.small,
            visualTransformation = when {
                isPasswordField && isPasswordVisible -> VisualTransformation.None
                isPasswordField -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            enabled = enabled
        )
    }
}