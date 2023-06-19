package com.padeltournaments.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.viewmodels.SearchViewModel

@Composable
fun SearchBar(searchViewModel: SearchViewModel,
              tournaments : List<TournamentEntity>?
){
    var textState by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, end = 8.dp, start = 8.dp, bottom = 0.dp),
        value = textState,
        onValueChange = { onQueryChanged ->
            textState = onQueryChanged

            if ( onQueryChanged.isEmpty() ){
                searchViewModel.setIsFiltering(false)
            }
            else searchViewModel.setIsFiltering(true)
            // Pass latest query to refresh search results.
            searchViewModel.performQuery(onQueryChanged, tournaments)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "SearchIcon"
            )
        },
        placeholder = { Text("Buscar") }
    )
}

@Composable
fun FilterLazyRow(
    searchViewModel: SearchViewModel = hiltViewModel(),
    allTournaments: List<TournamentEntity>
) {
    val categories = listOf(
        listOf("Primera", "Segunda", "Tercera"),
        listOf("De mas caro a mas barato", "De mas barato a mas caro"),
        listOf("De mayor a menor", "De menor a mayor"),
        listOf("Almería", "Cádiz", "Córdoba", "Granada",
                "Huelva", "Jaén", "Málaga", "Sevilla"
                )
    )

    val defaultSelected = listOf("Categoria", "Precio", "Premio", "Ubicacion")

    val selectedCategories = remember {
        mutableStateListOf(*defaultSelected.toTypedArray())
    }

    LazyRow(modifier = Modifier.fillMaxWidth(0.96f)) {
        items(defaultSelected.size) { index ->
            Column() {
                CustomDropdownMenu(
                    list = categories[index],
                    defaultSelected = selectedCategories[index],
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    onSelected = { selectedItem ->
                        selectedCategories[index] = selectedItem
                        if (selectedItem != defaultSelected[index]) {
                            when (index) {
                                0 -> searchViewModel.categoryVal = selectedItem
                                1 -> searchViewModel.cost = selectedItem
                                2 -> searchViewModel.prize = selectedItem
                                3 -> searchViewModel.ubication = selectedItem
                            }
                        }
                        searchViewModel.filterCombineFilters(
                            allTournaments,
                            searchViewModel.categoryVal,
                            searchViewModel.prize,
                            searchViewModel.cost,
                            searchViewModel.ubication
                        )
                        searchViewModel.setIsFiltering(true)
                    },
                    searchViewModel = searchViewModel,
                    allTournaments = allTournaments
                )
            }
        }
    }
}

@Composable
fun CustomDropdownMenu(
    list: List<String>,
    defaultSelected: String,
    color: Color,
    modifier: Modifier,
    onSelected: (String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
    allTournaments: List<TournamentEntity>
) {
    var expand by remember { mutableStateOf(false) }
    var stroke by remember { mutableStateOf(1) }

    Box(
        modifier
            .padding(top = 4.dp, end = 4.dp)
            .clickable {
                expand = true
                stroke = if (expand) 2 else 1
            }
            .background(color = White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = defaultSelected,
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        DropdownMenu(
            expanded = expand,
            onDismissRequest = {
                expand = false
                stroke = if (expand) 2 else 1
            },
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            modifier = Modifier
                .background(White)
                .padding(2.dp)
                .fillMaxWidth(.4f)
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onSelected(item)
                        expand = false
                        stroke = if (expand) 2 else 1
                    }
                ) {
                    Text(
                        text = item,
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun NoSearchResults() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text("No matches found")
    }
}