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
fun FilterLazyRow() {
    val categories = listOf(
        listOf("All", "Books", "Electronics", "Clothing", "Home"),
        listOf("All", "Fiction", "Non-Fiction", "Children's Books"),
        listOf("All", "Phones", "Laptops", "Tablets", "TVs")
    )

    val defaultSelected = listOf("Categoria", "Precio", "Premio")

    val selectedCategories = remember {
        mutableStateListOf(*defaultSelected.toTypedArray())
    }

    LazyRow (modifier = Modifier.fillMaxWidth(0.96f)){
        items(defaultSelected.size) { index ->
            Column() {
                CustomDropdownMenu(
                    list = categories[index],
                    defaultSelected = selectedCategories[index],
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    onSelected = { selectedIndex ->
                        selectedCategories[index] = categories[index][selectedIndex]
                    }
                )
            }
        }
    }
}

@Composable
fun CustomDropdownMenu(
    list: List<String>, // Menu Options
    defaultSelected: String, // Default Selected Option on load
    color: Color, // Color
    modifier: Modifier, //
    onSelected: (Int) -> Unit, // Pass the Selected Option
) {
    var selectedIndex by remember { mutableStateOf(0) }
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
            list.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expand = false
                        stroke = if (expand) 2 else 1
                        onSelected(selectedIndex)
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