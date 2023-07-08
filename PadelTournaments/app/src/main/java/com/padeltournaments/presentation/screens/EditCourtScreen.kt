package com.padeltournaments.presentation.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.presentation.composables.CustomTextInput
import com.padeltournaments.presentation.composables.PickImageFromGallery
import com.padeltournaments.presentation.composables.showDatePicker
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import com.padeltournaments.util.Category
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.TOURNAMENT_END_DATE
import com.padeltournaments.util.TOURNAMENT_STARTED_DATE

@Composable
fun EditCourtScreen(navController: NavController,
                    session : LoginPref,
                    createCourtViewModel: CreateCourtViewModel = hiltViewModel(),
                    idCourt: String?
){

    LaunchedEffect(idCourt) {
        if (idCourt != null) {
            createCourtViewModel.setCourtById(idCourt.toInt())
        }
    }

    val focusManager: FocusManager = LocalFocusManager.current

    ProvideWindowInsets {
        Column() {
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
            Column(
                Modifier
                    .navigationBarsWithImePadding()
                    .padding(24.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(text = "Crear Pista", fontSize = 40.sp)

                CustomTextInput(
                    value = createCourtViewModel.ubication.value,
                    onValueChange = {createCourtViewModel.onUbicationChange(it) },
                    label = "Ubicacion",
                    showError = !createCourtViewModel.validateUbication.value,
                    errorMessage = createCourtViewModel.validateUbicationError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Down)}
                    )
                )

                val andaluciaProvinces = listOf(
                    "Almería",
                    "Cádiz",
                    "Córdoba",
                    "Granada",
                    "Huelva",
                    "Jaén",
                    "Málaga",
                    "Sevilla"
                )

                var expanded by remember { mutableStateOf(false) }

                if (!createCourtViewModel.validateProvince.value) {
                    Text(
                        text = createCourtViewModel.validateProvinceError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(0.9f)
                    )
                }

                Surface(
                    elevation = 1.dp,
                ) {
                    Box {
                        ClickableText(
                            text = AnnotatedString(if (createCourtViewModel.province.value.isEmpty()) "Selecciona una provincia" else createCourtViewModel.province.value),
                            onClick = { expanded = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 12.dp)
                        )

                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Desplegar menú",
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    andaluciaProvinces.forEach { province ->
                        DropdownMenuItem(
                            onClick = {
                                createCourtViewModel.onProvinceChanged(province)
                                expanded = false
                            }
                        ) {
                            Text(text = province)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextInput(
                    value = createCourtViewModel.courtNumber.value,
                    onValueChange = {createCourtViewModel.onCourtNumberChange(it) },
                    label = "Numero de pista",
                    showError = !createCourtViewModel.validateCourtNumber.value,
                    errorMessage = createCourtViewModel.validateCourtNumberError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Down)}
                    )
                )

                CustomTextInput(
                    value = createCourtViewModel.bookCost.value,
                    onValueChange = {createCourtViewModel.onBookCostChange(it) },
                    label = "Coste Reserva(1 hora)",
                    showError = !createCourtViewModel.validateBookCost.value,
                    errorMessage = createCourtViewModel.validateBookCostError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Down)}
                    )
                )

                Button(onClick = {
                    if (createCourtViewModel.validateData()) {
                        session.getUserDetails()[LoginPref.KEY_ORG_ID]?.let {
                            val idOrg = it.toInt()

                            val courtEntity = idCourt?.let { it1 ->
                                CourtEntity(
                                    id = it1.toInt(),
                                    ubication= createCourtViewModel.ubication.value,
                                    courtNumber = createCourtViewModel.courtNumber.value,
                                    organizerId = idOrg,
                                    reservedHours = listOf<String>(),
                                    bookCost = createCourtViewModel.bookCost.value.toInt(),
                                    province = createCourtViewModel.province.value)
                            }
                            if (courtEntity != null) {
                                createCourtViewModel.updateCourt(courtEntity)
                            }

                            navController.navigate(NavigationScreens.HomeOrganizer.route)
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                    Text(text = "Editar Pista")
                }
            }

        }

    }
}
