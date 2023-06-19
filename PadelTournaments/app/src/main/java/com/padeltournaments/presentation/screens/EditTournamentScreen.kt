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
import com.padeltournaments.presentation.composables.CustomTextInput
import com.padeltournaments.presentation.composables.PickImageFromGallery
import com.padeltournaments.presentation.composables.showDatePicker
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.util.*

@Composable
fun EditTournamentScreen(context : Context,
                           navController: NavController,
                           session : LoginPref,
                           createTournamentViewModel: CreateTournamentViewModel = hiltViewModel(),
                           idTournament: String?
){
    val focusManager: FocusManager = LocalFocusManager.current
    LaunchedEffect(idTournament) {
        if (idTournament != null) {
            createTournamentViewModel.setTournamentById(idTournament.toInt())
        }
    }

    ProvideWindowInsets {
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

            Text(text = "Editar Torneo", fontSize = 40.sp)

            CustomTextInput(
                value = createTournamentViewModel.nameTournament.value,
                onValueChange = {createTournamentViewModel.onNameChanged(it) },
                label = "Nombre",
                showError = !createTournamentViewModel.validateName.value,
                errorMessage = createTournamentViewModel.validateNameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )

            CustomTextInput(
                value = createTournamentViewModel.inscriptionCost.value,
                onValueChange = {createTournamentViewModel.onInscriptionCostChanged(it) },
                label = "Precio de Inscripcion",
                showError = !createTournamentViewModel.validateInscriptionCost.value,
                errorMessage = createTournamentViewModel.validateInscriptionCostError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )

            CustomTextInput(
                value = createTournamentViewModel.prizeTournament.value,
                onValueChange = {createTournamentViewModel.onPrizeChanged(it) },
                label = "Premio",
                showError = !createTournamentViewModel.validatePrize.value,
                errorMessage = createTournamentViewModel.validatePrizeError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )

            CustomTextInput(
                value = createTournamentViewModel.maxNumberInscriptions.value,
                onValueChange = {createTournamentViewModel.onMaxNumberInscriptionsChanged(it) },
                label = "Maximo de participantes",
                showError = !createTournamentViewModel.validateMaxNumberInscriptions.value,
                errorMessage = createTournamentViewModel.validateMaxNumberInscriptionsError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )
            CustomTextInput(
                value = createTournamentViewModel.ubication.value,
                onValueChange = {createTournamentViewModel.onUbicationChanged(it) },
                label = "Ubicacion",
                showError = !createTournamentViewModel.validateUbication.value,
                errorMessage = createTournamentViewModel.validateUbicationError,
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

            if (!createTournamentViewModel.validateProvince.value) {
                Text(
                    text = createTournamentViewModel.validateProvinceError,
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
                        text = AnnotatedString(if (createTournamentViewModel.province.value.isEmpty()) "Selecciona una provincia" else createTournamentViewModel.province.value),
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
                            createTournamentViewModel.onProvinceChanged(province)
                            expanded = false
                        }
                    ) {
                        Text(text = province)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Seleccione Categoria")
            val selectedCategory = remember{
                createTournamentViewModel.category.value
            }
            Row {
                RadioButton(
                    selected = selectedCategory == Category.first,
                    onClick =
                    {
                        createTournamentViewModel.onCategoryChanged(Category.first)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Category.first)
                Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = selectedCategory == Category.second,
                    onClick =
                    {
                        createTournamentViewModel.onCategoryChanged(Category.second)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Category.second)
                Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = selectedCategory == Category.third,
                    onClick =
                    {
                        createTournamentViewModel.onCategoryChanged(Category.third)
                    },
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Category.third)
                Spacer(modifier = Modifier.size(16.dp))
            }

            showDatePicker(context,
                TOURNAMENT_STARTED_DATE,
                TOURNAMENT_END_DATE,
                createTournamentViewModel = createTournamentViewModel
            )

            PickImageFromGallery(createTournamentViewModel)

            Button(onClick = {
                session.getUserDetails()[LoginPref.KEY_ORG_ID]?.let {
                    var idOrg = session.getUserDetails()[LoginPref.KEY_ORG_ID]!!.toInt()
                    if (idTournament != null) {
                        createTournamentViewModel.updateTournament(idTournament.toInt(), idOrg)
                    }
                    navController.navigate(NavigationScreens.HomeOrganizer.route)
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                Text(text = "Editar Torneo")
            }
        }
    }
}