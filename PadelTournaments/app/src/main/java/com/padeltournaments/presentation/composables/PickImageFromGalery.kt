package com.padeltournaments.presentation.composables

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.padeltournaments.R
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import androidx.compose.material.*
import androidx.compose.ui.draw.clip
import com.padeltournaments.presentation.viewmodels.SignUpViewModel

@Composable
fun PickImageFromGallery(createTournamentViewModel: CreateTournamentViewModel) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember{ mutableStateOf<Bitmap?>(null)}

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
                createTournamentViewModel.onCartelChanged(bitmap.value!!)
                Log.d("Debuggingif", imageUri.toString())

            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
                createTournamentViewModel.onCartelChanged(bitmap.value!!)
                Log.d("Debuggingelse", imageUri.toString())
            }
        }

        createTournamentViewModel.poster.value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .padding(0.dp)
            )
        }
    }

    if (!createTournamentViewModel.validatePoster.value) {
        Text(
            text = createTournamentViewModel.validatePosterError,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(start = 8.dp)
                .offset(y = (-8).dp)
                .fillMaxWidth(0.9f)
        )
    }
    Button(onClick = { launcher.launch("image/*") }) {
        Text(text = "Cartel del Torneo")
    }
}

@Composable
fun PickImageFromGalleryRoundedImage(signUpViewModel: SignUpViewModel = hiltViewModel()) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember{ mutableStateOf<Bitmap?>(null)}

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
                signUpViewModel.onPhotoChanged(bitmap.value!!)

            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
                signUpViewModel.onPhotoChanged(bitmap.value!!)
            }
        }

        if( signUpViewModel.photo.value != null ) {
            signUpViewModel.photo.value?.let {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(0.dp)
                        .clip(CircleShape)
                        .clickable { launcher.launch("image/*") }
                ) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }/*
            RoundButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.padding(10.dp),
                contentDescription = "Botón redondo",
                iconResId = R.drawable.camera,
                backgroundColor = Color.LightGray,
                contentColor = Color.Black,
                isHidden = true
            )
            */
            */
        } else {
            RoundButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.padding(10.dp),
                contentDescription = "Botón redondo",
                iconResId = R.drawable.camera,
                backgroundColor = Color.LightGray,
                contentColor = Color.Black,
                isHidden = false
            )
        }
    }

    if (!signUpViewModel.validatePhoto.value) {
        Text(
            text = signUpViewModel.validatePhotorError,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(start = 8.dp)
                .offset(y = (-8).dp)
                .fillMaxWidth(0.9f)
        )
    }


}

@Composable
fun RoundButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconResId: Int,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    isHidden: Boolean = false
) {
    if(!isHidden) {
        Box(
            modifier = modifier
                .size(100.dp)
                .then(
                    Modifier.background(
                        color = backgroundColor,
                        shape = CircleShape
                    )
                )
        ) {
            Button(
                onClick = onClick,
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, color = Color.Transparent),
                colors = ButtonDefaults.buttonColors(backgroundColor),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = contentDescription,
                    tint = contentColor
                )
            }
        }
    }
}



