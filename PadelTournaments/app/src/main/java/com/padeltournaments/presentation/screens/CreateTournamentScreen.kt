package com.padeltournaments.presentation.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.PickImageFromGallery
import com.padeltournaments.presentation.composables.showDatePicker
import com.padeltournaments.presentation.navigation.NavigationScreens
import com.padeltournaments.util.*
import com.padeltournaments.presentation.theme.Shapes
import com.padeltournaments.presentation.viewmodels.CreateTournamentViewModel

@Composable
fun CreateTournamentScreen(context : Context,
                           navController: NavController,
                           session : LoginPref,
                           createTournamentViewModel: CreateTournamentViewModel = hiltViewModel()
){
    val passwordFocusRequester = FocusRequester()
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

            TextInput(
                inputType = InputType.TournamentName,
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus()
                }), createTournamentViewModel = createTournamentViewModel
            )

            TextInput(InputType.DeadlineDate, keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus()
            }), createTournamentViewModel = createTournamentViewModel)

            TextInput(InputType.Price, keyboardActions = KeyboardActions(onDone = {
                passwordFocusRequester.requestFocus()
            }), createTournamentViewModel = createTournamentViewModel)

            TextInput(InputType.InscriptionCost, keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }), focusRequester = passwordFocusRequester,
                createTournamentViewModel = createTournamentViewModel)

            Text(text = "Seleccione Categoria")
            val selectedCategory = remember{
                createTournamentViewModel.getCategory()
            }
            Row {
                RadioButton(
                    selected = selectedCategory.value == Category.first,
                    onClick =
                    {
                        createTournamentViewModel.onCategoryChanged(Category.first)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Category.first)
                Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = selectedCategory.value == Category.second,
                    onClick =
                    {
                        createTournamentViewModel.onCategoryChanged(Category.second)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(Category.second)
                Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = selectedCategory.value == Category.third,
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

                    val tournament = TournamentEntity(name = createTournamentViewModel.getNameTournament(),
                        inscriptionCost = createTournamentViewModel.getInscriptionCost(),
                        prize = createTournamentViewModel.getPrize(),
                        category = createTournamentViewModel.getCategory().value,
                        startDate = createTournamentViewModel.getDateIni(),
                        endDate = createTournamentViewModel.getDateEnd(),
                        poster = createTournamentViewModel.poster.value,
                        registrationDeadline = createTournamentViewModel.getDateLimit(),
                        idOrganizer = idOrg
                    )
                    Log.d("CREATETOURNAMENT", "Torneo: $tournament")
                    Log.d("CREATETOURNAMENT","ID USUARIO: $idOrg" )
                    createTournamentViewModel.insertTournament(tournament)
                    navController.navigate(NavigationScreens.HomeOrganizer.route)
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                Text(text = "Crear Torneo")
            }
        }
    }
}

@Composable
fun TextInput(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    createTournamentViewModel : CreateTournamentViewModel
) {
    val value: String
    when (inputType.label) {
        InputType.TournamentName.label -> {
            value = createTournamentViewModel.getNameTournament()
            TextField(
                value = value,
                onValueChange = { inputValue ->
                    createTournamentViewModel.onNameChanged(inputValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusOrder(focusRequester ?: FocusRequester()),
                leadingIcon = {
                    if (inputType.icon != null)
                        Icon(imageVector = inputType.icon, null)
                },
                label = { Text(text = inputType.label) },
                shape = Shapes.small,
                singleLine = true,
                keyboardOptions = inputType.keyboardOptions,
                visualTransformation = inputType.visualTransformation,
                keyboardActions = keyboardActions
            )

        }
        InputType.Price.label -> {
            value = createTournamentViewModel.getPrize()

            TextField(
                value = value,
                onValueChange = { inputValue ->
                    createTournamentViewModel.onPrizeChanged(inputValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusOrder(focusRequester ?: FocusRequester()),
                leadingIcon = {
                    if (inputType.icon != null)
                        Icon(imageVector = inputType.icon, null)
                },
                label = { Text(text = inputType.label) },
                shape = Shapes.small,
                singleLine = true,
                keyboardOptions = inputType.keyboardOptions,
                visualTransformation = inputType.visualTransformation,
                keyboardActions = keyboardActions
            )
        }
        InputType.InscriptionCost.label -> {
            value = createTournamentViewModel.getInscriptionCost()

            TextField(
                value = value,
                onValueChange = { inputValue ->
                    createTournamentViewModel.onInscriptionCostChanged(inputValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusOrder(focusRequester ?: FocusRequester()),
                leadingIcon = {
                    if (inputType.icon != null)
                        Icon(imageVector = inputType.icon, null)
                },
                label = { Text(text = inputType.label) },
                shape = Shapes.small,
                singleLine = true,
                keyboardOptions = inputType.keyboardOptions,
                visualTransformation = inputType.visualTransformation,
                keyboardActions = keyboardActions
            )
        }
        InputType.DeadlineDate.label -> {
            value = createTournamentViewModel.getDateLimit()

            TextField(
                value = value,
                onValueChange = { inputValue ->
                    createTournamentViewModel.onDateLimitChanged(inputValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusOrder(focusRequester ?: FocusRequester()),
                leadingIcon = {
                    if (inputType.icon != null)
                        Icon(imageVector = inputType.icon, null)
                },
                label = { Text(text = inputType.label) },
                shape = Shapes.small,
                singleLine = true,
                keyboardOptions = inputType.keyboardOptions,
                visualTransformation = inputType.visualTransformation,
                keyboardActions = keyboardActions
            )
        }
        else -> {
            Log.d("InputText", "No coincide el textfield con ningun label")
        }
    }
}
