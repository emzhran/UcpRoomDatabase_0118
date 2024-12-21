package com.example.ucp2_118.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_118.data.entity.Dosen
import com.example.ucp2_118.data.entity.MataKuliah
import com.example.ucp2_118.navigation.AlamatNavigasi
import com.example.ucp2_118.repository.RepositoryDsn
import com.example.ucp2_118.repository.RepositoryMatkul
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class UpdateMatkulViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatkul: RepositoryMatkul,
    private val repositoryDsn: RepositoryDsn
): ViewModel(){
    var updateUIState by mutableStateOf(MatkulUiState())
        private set

    var dosenUpdate by mutableStateOf<List<String>>(emptyList())
        private set

    val dosenState: StateFlow<List<Dosen>> = repositoryDsn.getAllDosen()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun fetchDosenUpdate(){
        viewModelScope.launch {
            try {
                repositoryDsn.getAllDosen()
                    .collect{dosenUpdateList->
                        dosenUpdate = dosenUpdateList.map { it.nama }
                    }
            }catch (e: Exception){
                dosenUpdate = emptyList()
            }
        }
    }

    private val _kode: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiUpdateMatkul.KODE])
    init {
        viewModelScope.launch {
            updateUIState = repositoryMatkul.getMatakuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMatkul()
        }
    }
    fun updateState(matkulEvent: MatkulEvent){
        updateUIState = updateUIState.copy(
            matkulEvent = matkulEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.matkulEvent
        val errorState = MatkulErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama matkul tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty())null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen pengampu tidak boleh kosong"
        )
        updateUIState = updateUIState.copy(isEntryValid =  errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUIState.matkulEvent
        if(validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMatkul.updateMatakuliah(currentEvent.toMatakuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        matkulEvent = MatkulEvent(),
                        isEntryValid = MatkulErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackbarMessage}")
                } catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data gagal diupdate"
                    )
                }
            }
        }else{
            updateUIState = updateUIState.copy(
                snackbarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackbarMessage = null)
    }
}


fun MataKuliah.toUIStateMatkul(): MatkulUiState = MatkulUiState(
    matkulEvent = this.toDetailUiEvent()
)