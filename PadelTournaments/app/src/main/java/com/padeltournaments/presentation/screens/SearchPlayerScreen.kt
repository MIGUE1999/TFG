package com.padeltournaments.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.padeltournaments.data.entities.CourtEntity
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.presentation.composables.*
import com.padeltournaments.presentation.composables.FilterLazyRow
import com.padeltournaments.presentation.composables.scaffold.BottomBar
import com.padeltournaments.presentation.composables.scaffold.TopBar
import com.padeltournaments.presentation.viewmodels.CreateCourtViewModel
import com.padeltournaments.presentation.viewmodels.HomeOrganizerViewModel
import com.padeltournaments.presentation.viewmodels.SearchViewModel
import com.padeltournaments.util.LoginPref
import com.padeltournaments.util.playerScreens

@Composable
fun SearchPlayerScreen(session : LoginPref,
                          searchViewModel : SearchViewModel = hiltViewModel(),
                          navController : NavHostController,
                        createCourtViewModel: CreateCourtViewModel = hiltViewModel()) {

    val allTournaments = searchViewModel.getAllTournaments()
        .collectAsState(initial = emptyList())

    val allCourts = searchViewModel.getAllCourts()
        .collectAsState(initial = emptyList())

    val filteredList by searchViewModel.filteredList.observeAsState(emptyList())
    val filteredCourtList by searchViewModel.filteredCourtList.observeAsState(emptyList())

    Scaffold(
        bottomBar = { BottomBar(navController = navController, playerScreens) },
        content = { TabBarSearchPlayer(navController = navController, tournaments = allTournaments.value,
            filteredList = filteredList, searchViewModel = searchViewModel, isOrganizer = false, session = session, courts = allCourts.value.toList(), filteredCourtList = filteredCourtList) },
    )
}

@Composable
fun TabBarSearchPlayer(navController : NavHostController,
                 tournaments: List<TournamentEntity>,
                 courts: List<CourtEntity>,
                 session: LoginPref,
                 filteredList: List<TournamentEntity>,
                 searchViewModel : SearchViewModel,
                 isOrganizer : Boolean,
                 filteredCourtList: List<CourtEntity>
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .height(100.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = "TORNEOS",
                        modifier = Modifier.weight(1f)
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = "PISTAS",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        // Aquí puedes agregar el contenido correspondiente a cada pestaña
        val idUser = session.getUserDetails()[(LoginPref.KEY_ID)]!!.toInt()

        if (selectedTabIndex == 0) {
            SearchContent(navController = navController, tournaments, filteredList, searchViewModel, isOrganizer = isOrganizer)
        } else {
            SearchCourt(
                navController = navController,
                courts = courts,
                courtsFiltered = filteredCourtList,
                searchViewModel = searchViewModel,
                isOrganizer = isOrganizer,
                idUser = idUser
            )
        }

    }

}

@Composable
fun SearchCourt(navController: NavHostController,
                courts : List<CourtEntity>,
                courtsFiltered : List<CourtEntity>?,
                searchViewModel: SearchViewModel,
                isOrganizer: Boolean,
                idUser: Int){
    val isFiltering by searchViewModel.isFilteringCourt.observeAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth())
    {
        FilterCourtLazyRow(searchViewModel, courts)

        CourtList(isOrganizer = isOrganizer, navController = navController, courts = courts, idUser = idUser, isSearch = true)

    }
}


@Composable
fun FilterCourtLazyRow(
    searchViewModel: SearchViewModel = hiltViewModel(),
    allCourts: List<CourtEntity>
) {
    val categories = listOf(
        listOf("De mas caro a mas barato", "De mas barato a mas caro"),
        listOf("Almería", "Cádiz", "Córdoba", "Granada",
            "Huelva", "Jaén", "Málaga", "Sevilla"
        )
    )

    val defaultSelected = listOf("Precio", "Ubicacion")

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
                                0 -> searchViewModel.courtCost = selectedItem
                                1 -> searchViewModel.courtUbication = selectedItem
                            }
                        }
                        searchViewModel.filterCombineCourtFilters(
                            allCourts,
                            ubication = searchViewModel.courtUbication,
                            cost = searchViewModel.courtCost,

                        )
                        searchViewModel.setIsFilteringCourt(true)
                    }
                )
            }
        }
    }
}


