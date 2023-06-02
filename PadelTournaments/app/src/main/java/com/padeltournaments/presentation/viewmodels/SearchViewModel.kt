package com.padeltournaments.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.padeltournaments.data.entities.TournamentEntity
import com.padeltournaments.data.repository.interfaces.ITournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tournamentRepository : ITournamentRepository,
) : ViewModel() {

    private val filteredListValue = ArrayList<TournamentEntity>()
    private val filteredCategoriesListValue = ArrayList<TournamentEntity>()

    private val _filteredList = MutableLiveData<List<TournamentEntity>>()
    val filteredList: LiveData<List<TournamentEntity>> get() = _filteredList

    private val _isFiltering = MutableLiveData(false)
    val isFiltering: LiveData<Boolean> get() = _isFiltering

    fun getAllTournaments() : Flow<List<TournamentEntity>> {
        return tournamentRepository.getAllTournaments()
    }

     fun performQuery(query: String, tournaments : List<TournamentEntity>?) {
         if( query == "" ) {
             tournaments?.let {
                 _filteredList.value = it
             }
             return
         }
        // New empty array list which contains filtered list with query.
         if (filteredListValue.isNotEmpty() ) {
             filteredListValue.clear()
         }
        // Loop into each actors data to read actors name.
        tournaments?.forEach { tournament ->
            print(tournament.name)
            // Compare query with actors name to find a match.
            if (tournament.name.lowercase().contains(query.lowercase())) {
                // If match is found, add that name to filtered list.
                filteredListValue.add(tournament)
            }
        }
         if (filteredListValue.isNotEmpty()){
             _filteredList.value = filteredListValue.toList()
         } else {
             _filteredList.value = emptyList()
         }
    }

    fun filterTournamentByCategory(allTournaments : List<TournamentEntity>, category: String) {
        filteredCategoriesListValue.clear()
        allTournaments.forEach { tournament ->
            if (tournament.category == category) {
                filteredCategoriesListValue.add(tournament)
            }
        }
        _filteredList.value = filteredCategoriesListValue.toList()
        setIsFiltering(true)
    }

    fun filterTournamentByPrize(allTournaments : List<TournamentEntity>, prize: String) {

        filteredCategoriesListValue.clear()

        allTournaments.forEach { tournament ->
            if (tournament.prize == prize) {
                filteredCategoriesListValue.add(tournament)
            }
        }
        setIsFiltering(true)
    }

    fun filterTournamentByCost(allTournaments : List<TournamentEntity>, cost: String) {
        filteredCategoriesListValue.clear()
        allTournaments.forEach { tournament ->
            if (tournament.category == cost) {
                filteredCategoriesListValue.add(tournament)
            }
        }
    }

    fun filterTournamentByLocation(allTournaments : List<TournamentEntity>, location: String) {
        filteredCategoriesListValue.clear()
        allTournaments.forEach { tournament ->
            if (tournament.category == location) {
                filteredCategoriesListValue.add(tournament)
            }
        }
    }

    fun setIsFiltering(isFiltering: Boolean){
        _isFiltering.value = isFiltering
    }
}
