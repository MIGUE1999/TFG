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
    private var filteredPrizeListValue = ArrayList<TournamentEntity>()
    private var filteredCostListValue = ArrayList<TournamentEntity>()
    private var filteredUbicationListValue = ArrayList<TournamentEntity>()


    var categoryVal: String? = null
    var prize: String? = null
    var cost: String? = null
    var ubication: String? = null

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


    fun filterCombineFilters(allTournaments : List<TournamentEntity>, category: String?, prize: String?, cost: String?, ubication: String?) {
        _filteredList.value = emptyList()
        if (category != null){
            filterTournamentByCategory(allTournaments, category)
            if (prize != null){
                filterTournamentByPrize(_filteredList.value!!, prize)
                if (cost != null){
                    filterTournamentByCost(_filteredList.value!!, cost)
                    if(ubication != null){
                        filterTournamentByUbication(_filteredList.value!!, ubication)
                    }
                }
            } else if (cost != null){
                filterTournamentByCost(_filteredList.value!!, cost)
                if(ubication != null){
                    filterTournamentByUbication(_filteredList.value!!, ubication)
                }
            } else if(ubication != null){
                filterTournamentByUbication(_filteredList.value!!, ubication)
            }
        } else if(prize != null) {
            filterTournamentByPrize(allTournaments, prize)
            if (cost != null){
                filterTournamentByCost(_filteredList.value!!, cost)
                if(ubication != null){
                    filterTournamentByUbication(_filteredList.value!!, ubication)
                }
            }
            else if(ubication != null){
                filterTournamentByUbication(_filteredList.value!!, ubication)
            }
        } else if(cost != null){
            filterTournamentByCost(allTournaments, cost)
            if(ubication != null){
                filterTournamentByUbication(_filteredList.value!!, ubication)
            }
        } else if(ubication != null){
            filterTournamentByUbication(allTournaments, ubication)
        }
        else { _filteredList.value = allTournaments}
    }

    private fun filterTournamentByCategory(allTournaments : List<TournamentEntity>, category: String) {
        filteredCategoriesListValue.clear()
        allTournaments.forEach { tournament ->
            if (tournament.category == category) {
                filteredCategoriesListValue.add(tournament)
            }
        }
        _filteredList.value = filteredCategoriesListValue.toList()
        //setIsFiltering(true)
    }

    private fun filterTournamentByPrize(allTournaments : List<TournamentEntity>, prize: String) {
        filteredPrizeListValue.clear()
        filteredPrizeListValue = when (prize) {
            "De mayor a menor" -> ArrayList(allTournaments.sortedByDescending { it.prize })
            "De menor a mayor" -> ArrayList(allTournaments.sortedBy { it.prize })
            else -> ArrayList(allTournaments) // Orden predeterminado si no coincide con ninguna opción
        }
        _filteredList.value = filteredPrizeListValue
        //setIsFiltering(true)
    }
    private fun filterTournamentByCost(allTournaments : List<TournamentEntity>, cost: String) {
        filteredCostListValue.clear()
        filteredCostListValue = when (cost) {
            "De mas caro a mas barato" -> ArrayList(allTournaments.sortedByDescending { it.inscriptionCost })
            "De mas barato a mas caro" -> ArrayList(allTournaments.sortedBy { it.inscriptionCost })
            else -> ArrayList(allTournaments) // Orden predeterminado si no coincide con ninguna opción
        }
        _filteredList.value = filteredCostListValue
        //setIsFiltering(true)
    }
    fun filterTournamentByUbication(allTournaments : List<TournamentEntity>, location: String) {
        filteredUbicationListValue.clear()
        allTournaments.forEach { tournament ->
            if (tournament.province == location) {
                filteredUbicationListValue.add(tournament)
            }
            _filteredList.value = filteredUbicationListValue.toList()
        }
    }
    fun setIsFiltering(isFiltering: Boolean){
        _isFiltering.value = isFiltering
    }
}
