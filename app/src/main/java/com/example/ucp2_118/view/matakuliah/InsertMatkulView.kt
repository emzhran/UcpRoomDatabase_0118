package com.example.ucp2_118.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_118.repository.RepositoryDsn
import com.example.ucp2_118.ui.viewmodel.MatkulErrorState
import com.example.ucp2_118.ui.viewmodel.MatkulEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMatakuliah(
    matkulEvent: MatkulEvent = MatkulEvent(),
    onValueChange:(MatkulEvent) -> Unit = {},
    errorState: MatkulErrorState = MatkulErrorState(),
    dosenList: List<String> = emptyList(),
    modifier: Modifier = Modifier
){
    val jenis = listOf("Ganjil", "Genap")
    var expanded by remember { mutableStateOf(false) }
    var selectedDosen by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.kode,
            onValueChange ={
                onValueChange(matkulEvent.copy(kode = it))
            },
            label = { Text("Kode") },
            isError = errorState.kode != null,
            placeholder ={ Text("Kode MataKuliah") },
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.nama,
            onValueChange = {
                onValueChange(matkulEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Nama MataKuliah") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.sks,
            onValueChange = {
                onValueChange(matkulEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Jumlah SKS") },
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.semester,
            onValueChange = {
                onValueChange(matkulEvent.copy(semester = it))
            },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Semester") },
        )
        Text(
            text = errorState.semester ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Jenis Matakuliah")
        Row(modifier = Modifier.fillMaxWidth()) {
            jenis.forEach{jm->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matkulEvent.jenis == jm,
                        onClick = {
                            onValueChange(matkulEvent.copy(jenis = jm))
                        },
                    )
                    Text(text = jm)
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Dosen Pengampu")
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded}
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = selectedDosen,
                onValueChange = {},
                readOnly = true,
                label = { Text("Dosen Pengampu") },
                placeholder = { Text("Pilih dosen pengampu") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors =  ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}
            ) {
                dosenList.forEach { dosen->
                    DropdownMenuItem(
                        text = { Text(dosen) },
                        onClick = {
                            selectedDosen = dosen
                            onValueChange(matkulEvent.copy(dosenPengampu = dosen))
                            expanded = false
                        }
                    )
                }
            }
        }
        Text(
            text = errorState.dosenPengampu ?:"",
            color = Color.Red
        )
    }
}