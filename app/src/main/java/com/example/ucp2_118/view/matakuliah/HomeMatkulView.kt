package com.example.ucp2_118.view.matakuliah

import com.example.ucp2_118.data.entity.MataKuliah

data class HomeUIState(
    val listMatkul: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)