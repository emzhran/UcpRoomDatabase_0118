package com.example.ucp2_118.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_118.data.entity.Dosen
import com.example.ucp2_118.data.entity.MataKuliah
import com.example.ucp2_118.repository.LocalRepositoryMatkul
import com.example.ucp2_118.repository.RepositoryDsn
import com.example.ucp2_118.repository.RepositoryMatkul
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MatkulViewModel(private val  repositoryMatkul: RepositoryMatkul,
    private val repositoryDsn: RepositoryDsn) : ViewModel(){

    var uiState by mutableStateOf(MatkulUiState())

    val dosenList: StateFlow<List<Dosen>> = repositoryDsn.getAllDosen()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var dosenInsert by mutableStateOf<List<String>>(emptyList())
        private set

    fun fetchNamaDosen(){
        viewModelScope.launch {
            try {
                repositoryDsn.getAllDosen()
                    .collect{dosenlist->
                        dosenInsert = dosenlist.map { it.nama }
                    }
            } catch (e: Exception){
                dosenInsert = emptyList()
            }
        }
    }

    fun updateState(matkulEvent: MatkulEvent){
        uiState = uiState.copy(
            matkulEvent = matkulEvent
        )
    }

    private fun validateFields(): Boolean{
        val event = uiState.matkulEvent
        val errorState = MatkulErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama matkul tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveData(){
        val currentEvent = uiState.matkulEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMatkul.insertMatakuliah(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        matkulEvent = MatkulEvent(),
                        isEntryValid = MatkulErrorState()
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(snackbarMessage = "Data gagal disimpan")
                }
            }
        }else{
            uiState = uiState.copy(snackbarMessage = "Input tidak valid. Periksa kembali data")
        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackbarMessage = null)
    }
}

data class MatkulUiState(
    val matkulEvent: MatkulEvent = MatkulEvent(),
    val isEntryValid: MatkulErrorState = MatkulErrorState(),
    val snackbarMessage : String? = null
)

data class MatkulErrorState(
    val kode : String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null && semester == null && jenis == null && dosenPengampu == null
    }
}

fun MatkulEvent.toMatakuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MatkulEvent(
    val kode : String = "",
    val nama : String = "",
    val sks : String = "",
    val semester : String = "",
    val jenis : String = "",
    val dosenPengampu : String = ""
)