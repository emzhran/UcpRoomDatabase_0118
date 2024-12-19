package com.example.ucp2_118.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_118.data.entity.Dosen
import com.example.ucp2_118.navigation.AlamatNavigasi
import com.example.ucp2_118.repository.LocalRepositoryDsn
import com.example.ucp2_118.repository.RepositoryDsn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DetailDsnViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryDsn: RepositoryDsn
): ViewModel(){
    private val _nidn: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiDetailDosen.NIDN])
    val detailUiState: StateFlow<DetailUiState> = repositoryDsn.getDosen(_nidn)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    isErrorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true
            )
        )
}

data class DetailUiState(
    val detailUiEvent: DosenEvent = DosenEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isErrorMessage: String = ""
){
    val  isUiEventEmpty: Boolean
        get() = detailUiEvent == DosenEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DosenEvent()
}

fun Dosen.toDetailUiEvent():DosenEvent{
    return DosenEvent(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jenisKelamin
    )
}