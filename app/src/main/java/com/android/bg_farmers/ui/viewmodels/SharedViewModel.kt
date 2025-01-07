package com.android.bg_farmers.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bg_farmers.data.models.Farmer
import com.android.bg_farmers.data.repositories.DataStoreRepository
import com.android.bg_farmers.data.repositories.FarmerRepository
import com.android.bg_farmers.util.Action
import com.android.bg_farmers.util.RequestState
import com.android.bg_farmers.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: FarmerRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _searchedTasks = MutableStateFlow<RequestState<List<Farmer>>>(RequestState.Idle)
    val searchedFarmers: StateFlow<RequestState<List<Farmer>>> = _searchedTasks

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val name: MutableState<String> = mutableStateOf("")
    val cropType: MutableState<String> = mutableStateOf("")

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allFarmers = MutableStateFlow<RequestState<List<Farmer>>>(RequestState.Idle)
    val allFarmers: StateFlow<RequestState<List<Farmer>>> = _allFarmers

    fun getAllFarmers() {
        _allFarmers.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllFarmers.collect {
                    _allFarmers.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allFarmers.value = RequestState.Error(e)
        }
    }

    private val _selectedFarmer: MutableStateFlow<Farmer?> = MutableStateFlow(null)
    val selectedFarmer: StateFlow<Farmer?> = _selectedFarmer

    fun getSelectedFarmer(farmerId: Int) {
        viewModelScope.launch {
            repository.getSelectedFarmer(farmerId).collect { task ->
                _selectedFarmer.value = task
            }
        }
    }

    fun updateFarmerFields(selectedFarmer: Farmer?) {
        if (selectedFarmer != null) {
            id.value = selectedFarmer.id
            name.value = selectedFarmer.name
            cropType.value = selectedFarmer.cropType
        } else {
            id.value = 0
            name.value = ""
            cropType.value = ""
        }
    }

    fun validateFields(): Boolean {
        return name.value.isNotEmpty() && cropType.value.isNotEmpty()
    }

    private fun addFarmer() {
        viewModelScope.launch(Dispatchers.IO) {
            val farmer = Farmer(
                name = name.value,
                cropType = cropType.value,
            )
            repository.addFarmer(farmer)
        }
        searchTextState.value = ""
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateFarmer() {
        viewModelScope.launch(Dispatchers.IO) {
            val farmer = Farmer(
                id = id.value,
                name = name.value,
                cropType = cropType.value,
            )
            repository.updateFarmer(farmer)
        }
    }

    private fun deleteFarmer() {
        viewModelScope.launch(Dispatchers.IO) {
            val farmer = Farmer(
                id = id.value,
                name = name.value,
                cropType = cropType.value,
            )
            repository.deleteFarmer(farmer)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFarmers()
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> { addFarmer() }
            Action.UPDATE -> { updateFarmer() }
            Action.DELETE -> { deleteFarmer() }
            Action.DELETE_ALL -> { deleteAllTask() }
            Action.UNDO -> { addFarmer() }
            else -> { }
        }
        this.action.value = Action.NO_ACTION
    }

    fun getSearchedFarmers(searchedQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase("%$searchedQuery%").collect {
                    _searchedTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }
}