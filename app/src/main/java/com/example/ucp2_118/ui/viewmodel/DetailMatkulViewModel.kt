package com.example.ucp2_118.ui.viewmodel

import com.example.ucp2_118.data.entity.MataKuliah

data class DetailUiState(
    val detailUiEvent: MatkulEvent = MatkulEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

fun MataKuliah.toDetailUiEvent():MatkulEvent{
    return MatkulEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}