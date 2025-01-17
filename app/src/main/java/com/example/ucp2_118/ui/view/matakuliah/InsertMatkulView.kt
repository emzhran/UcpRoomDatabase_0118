package com.example.ucp2_118.ui.view.matakuliah

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_118.data.entity.Dosen
import com.example.ucp2_118.navigation.AlamatNavigasi
import com.example.ucp2_118.ui.customwidget.TopAppBar
import com.example.ucp2_118.ui.viewmodel.MatkulErrorState
import com.example.ucp2_118.ui.viewmodel.MatkulEvent
import com.example.ucp2_118.ui.viewmodel.MatkulUiState
import com.example.ucp2_118.ui.viewmodel.MatkulViewModel
import com.example.ucp2_118.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertMatkul : AlamatNavigasi {
    override val route: String = "insert_matkul"
}

@Composable
fun InsertMatkulView(
    onBack:()-> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatkulViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val dosenList by viewModel.dosenList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchNamaDosen()
    }

    LaunchedEffect(uiState.matkulEvent) {
        uiState.snackbarMessage?.let { message->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold(
        containerColor = Color(0xFF0B4D4D),
        topBar ={
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah"
            )
        },
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(15.dp)
        ) {
            InsertBodyMatkul(
                uiState = uiState,
                onValueChange ={ updateEvent->
                    viewModel.updateState(updateEvent)
                },
                dosenList = dosenList,
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                },
            )
        }
    }
}

@Composable
fun InsertBodyMatkul(
    modifier: Modifier = Modifier,
    onValueChange: (MatkulEvent) -> Unit,
    uiState: MatkulUiState,
    onClick: () -> Unit,
    dosenList: List<Dosen>
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { FormMatakuliah(
        matkulEvent = uiState.matkulEvent,
        onValueChange = onValueChange,
        errorState = uiState.isEntryValid,
        dosenList =  dosenList,
        modifier = Modifier.fillMaxWidth()
    )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMatakuliah(
    matkulEvent: MatkulEvent = MatkulEvent(),
    onValueChange:(MatkulEvent) -> Unit = {},
    errorState: MatkulErrorState = MatkulErrorState(),
    dosenList: List<Dosen> = emptyList(),
    modifier: Modifier = Modifier
){
    val jenis = listOf("Wajib", "Pilihan")
    val semester = listOf("Ganjil", "Genap")

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.kode,
            onValueChange ={
                onValueChange(matkulEvent.copy(kode = it))
            },
            label = { Text("Kode", color = Color.White) },
            isError = errorState.kode != null,
            placeholder ={ Text("Kode MataKuliah", color = Color.White) },
            textStyle = TextStyle(color = Color.White)
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
            label = { Text("Nama Matakuliah", color = Color.White) },
            isError = errorState.nama != null,
            placeholder = { Text("Nama MataKuliah", color = Color.White) },
            textStyle = TextStyle(color = Color.White)
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
            label = { Text("SKS", color = Color.White) },
            isError = errorState.sks != null,
            placeholder = { Text("Jumlah SKS", color = Color.White) },
            textStyle = TextStyle(color = Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Semester Matakuliah", color = Color.White)
        Row(modifier = Modifier.fillMaxWidth()) {
            semester.forEach{smstr->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matkulEvent.semester == smstr,
                        onClick = {
                            onValueChange(matkulEvent.copy(semester = smstr))
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.White
                        )
                    )
                    Text(text = smstr, color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Jenis Matakuliah", color = Color.White)
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
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.White
                        )
                    )
                    Text(text = jm, color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Dosen Pengampu", color = Color.White)
        var expanded by remember { mutableStateOf(false) }
        var selectedDosen by remember { mutableStateOf("") }
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            OutlinedTextField(
                value = selectedDosen,
                onValueChange = { },
                label = { Text("Pilih Dosen", color = Color.White) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
                    .clickable { expanded = true },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { expanded = true }
                    )
                },
                textStyle = TextStyle(color = Color.White)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false},
                modifier = Modifier.fillMaxWidth()
            ) {
                dosenList.forEach { dosen->
                    DropdownMenuItem(
                        text = { Text(dosen.nama, color = Color.Black) },
                        onClick = {
                            selectedDosen = dosen.nama
                            onValueChange(matkulEvent.copy(dosenPengampu = dosen.nama))
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}