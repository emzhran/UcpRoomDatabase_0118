package com.example.ucp2_118.ui.viewmodel

import com.example.ucp2_118.data.entity.Dosen


fun DosenEvent.toDosenEntity():Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)

data class DosenEvent(
    val nidn : String = "",
    val nama : String = "",
    val jenisKelamin : String = ""
)