package com.example.ucp2_118.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_118.data.entity.Dosen
import com.example.ucp2_118.repository.RepositoryDsn
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryDsn: RepositoryDsn):ViewModel(){
    var uiState by mutableStateOf(DsnUIState())

    fun updateState(dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent
        )
    }

    private fun validateFields(): Boolean{
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.dosenEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryDsn.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        dosenEvent = DosenEvent(),
                        isEntryValid = FormErrorState()
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(snackbarMessage = "Data gagal disimpan")
                }
            }
        }else{
            uiState = uiState.copy(snackbarMessage = "Input tidak valid, periksa kembali data")
        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackbarMessage = null)
    }
}

data class DsnUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null
)

data class FormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
){
    fun isValid(): Boolean{
        return nidn == null && nama == null && jenisKelamin == null
    }
}

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