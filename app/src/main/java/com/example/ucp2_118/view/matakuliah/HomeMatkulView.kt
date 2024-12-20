package com.example.ucp2_118.view.matakuliah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_118.data.entity.MataKuliah
import com.example.ucp2_118.repository.RepositoryMatkul
import com.example.ucp2_118.ui.viewmodel.HomeUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMatkulView(
    private val repositoryMatkul: RepositoryMatkul
): ViewModel(){

    val HomeUIState: StateFlow<HomeUIState> = repositoryMatkul.getAllMatakuliah()
        .filterNotNull()
        .map {
            HomeUIState(
                listMatkul = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUIState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUIState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUIState(
                isLoading = true
            )
        )
}

data class HomeUIState(
    val listMatkul: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)