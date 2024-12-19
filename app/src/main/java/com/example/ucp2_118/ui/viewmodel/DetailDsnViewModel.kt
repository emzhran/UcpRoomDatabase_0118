package com.example.ucp2_118.ui.viewmodel

import com.example.ucp2_118.data.entity.Dosen

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