package com.example.ucp2_118.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_118.data.entity.MataKuliah
import com.example.ucp2_118.repository.LocalRepositoryMatkul
import com.example.ucp2_118.repository.RepositoryMatkul
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMatkulViewModel(
    private val repositoryMatkul: RepositoryMatkul
): ViewModel(){
    val HomeMatkulState: StateFlow<HomeMatkulState> = repositoryMatkul.getAllMatakuliah()
        .filterNotNull()
        .map {
            HomeMatkulState(
                listMatkul = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeMatkulState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeMatkulState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeMatkulState(
                isError = true,
            )
        )
}

data class HomeMatkulState(
    val listMatkul: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)