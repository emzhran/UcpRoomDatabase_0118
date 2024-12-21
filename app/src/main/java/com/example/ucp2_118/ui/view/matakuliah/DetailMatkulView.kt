package com.example.ucp2_118.ui.view.matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_118.data.entity.MataKuliah
import com.example.ucp2_118.ui.customwidget.TopAppBar
import com.example.ucp2_118.ui.viewmodel.DetailMatkulViewModel
import com.example.ucp2_118.ui.viewmodel.DetailUiState
import com.example.ucp2_118.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_118.ui.viewmodel.toMatakuliahEntity

@Composable
fun DetailMatkulView(
    modifier: Modifier = Modifier,
    viewModel: DetailMatkulViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: ()-> Unit = {},
    onEdit:(String)-> Unit = {},
    onDeleteClick:()->Unit = {}
){
    Scaffold(
        topBar ={
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Detail Matakuliah"
            )
        },
        floatingActionButton ={
            FloatingActionButton(
                onClick ={
                    onEdit(viewModel.detailUiState.value.detailUiEvent.kode)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Matakuliah"
                )
            }
        }
    ) { innerpadding->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailMatkul(
            modifier = Modifier.padding(innerpadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMatkul()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailMatkul(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = { }
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when{
        detailUiState.isLoading->{
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        detailUiState.isUiEventNotEmpty->{
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMatkul(
                    mataKuliah = detailUiState.detailUiEvent.toMatakuliahEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick ={
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
                if (deleteConfirmationRequired){
                    DeleteConfirmationDialog(
                        onDeleteConfirmation ={
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel ={deleteConfirmationRequired = false},
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        detailUiState.isUiEventEmpty->{
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMatkul(
    modifier: Modifier = Modifier,
    mataKuliah: MataKuliah
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailMatkul(judul = "Kode", isinya = mataKuliah.kode)
            Spacer(modifier = Modifier.padding(5.dp))

            ComponentDetailMatkul(judul = "Nama", isinya = mataKuliah.nama)
            Spacer(modifier = Modifier.padding(5.dp))

            ComponentDetailMatkul(judul = "SKS", isinya = mataKuliah.sks)
            Spacer(modifier  = Modifier.padding(5.dp))

            ComponentDetailMatkul(judul = "Semester", isinya = mataKuliah.semester)
            Spacer(modifier = Modifier.padding(5.dp))

            ComponentDetailMatkul(judul = "Jenis", isinya = mataKuliah.jenis)
            Spacer(modifier = Modifier.padding(5.dp))

            ComponentDetailMatkul(judul = "Dosen Pengampu", isinya = mataKuliah.dosenPengampu)
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ComponentDetailMatkul(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirmation:()->Unit,
    onDeleteCancel:()->Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { /* Do Nothing */},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirmation) {
                Text(text = "Yes")
            }
        })
}