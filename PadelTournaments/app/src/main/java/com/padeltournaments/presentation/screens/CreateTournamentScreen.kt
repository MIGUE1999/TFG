package com.padeltournaments.presentation.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.presentation.composables.CustomTextInput
import com.padeltournaments.presentation.composables.PickImageFromGallery
import com.padeltournaments.presentation.composables.showDatePicker
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.util.*
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import kotlinx.coroutines.delay

@Composable
fun CreateTournamentScreen(context : Context,
                           navController: NavController,
                           session : LoginPref,
                           createTournamentViewModel: CreateTournamentViewModel = hiltViewModel(),
                           homeOrganizerViewModel: HomeOrganizerViewModel = hiltViewModel()
){
    val focusManager: FocusManager = LocalFocusManager.current

    ProvideWindowInsets {
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Crear Torneo", fontSize = 40.sp)

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

            Text(text = "Seleccione Categoria")
            Row {
                RadioButton(
                    selected = createTournamentViewModel.category.value == Category.first,
                    onClick =
                    {
                        createTournamentViewModel.onCategoryChanged(Category.first)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Category.first)
                Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = createTournamentViewModel.category.value == Category.second,
                    onClick =
                    {
                        createTournamentViewModel.onCategoryChanged(Category.second)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Category.second)
                Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = createTournamentViewModel.category.value == Category.third,
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
                createTournamentViewModel = createTournamentViewModel,
                showError = !createTournamentViewModel.validateDate.value,
                errorMessage = createTournamentViewModel.validateDateError,
            )

            PickImageFromGallery(createTournamentViewModel)

            Button(onClick = {
                if (createTournamentViewModel.validateData()) {
                    session.getUserDetails()[LoginPref.KEY_ORG_ID]?.let {
                        val idOrg = it.toInt()
                        createTournamentViewModel.createTournament(idOrg)
                        navController.navigate(NavigationScreens.HomeOrganizer.route)
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                Text(text = "Crear Torneo")
            }
        }
    }
}
