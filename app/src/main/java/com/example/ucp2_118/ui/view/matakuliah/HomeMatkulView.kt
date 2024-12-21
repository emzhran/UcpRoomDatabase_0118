package com.example.ucp2_118.ui.view.matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_118.data.entity.MataKuliah
import com.example.ucp2_118.ui.customwidget.TopAppBar
import com.example.ucp2_118.ui.viewmodel.HomeMatkulState
import com.example.ucp2_118.ui.viewmodel.HomeMatkulViewModel
import com.example.ucp2_118.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeMatkulView(
    viewModel: HomeMatkulViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddMatkul:()-> Unit ={},
    onDetailClick:(String)-> Unit = {},
    onBack:()-> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        containerColor = Color(0xFF0B4D4D),
        topBar ={
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Daftar Matakuliah"
            )
        },
        floatingActionButton ={
            FloatingActionButton(
                onClick = onAddMatkul,
                shape = MaterialTheme.shapes.medium,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Matakuliah"
                )
            }
        }
    ) { innerpadding->
        val homeUiState by viewModel.HomeMatkulState.collectAsState()

        BodyMatkulView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerpadding)
        )
    }
}


@Composable
fun BodyMatkulView(
    homeUiState: HomeMatkulState,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when{
        homeUiState.isLoading->{
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeUiState.isError->{
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeUiState.listMatkul.isEmpty()->{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data matakuliah.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else->{
            listMatkul(
                listMatkul = homeUiState.listMatkul,
                onClick = {
                    onClick(it)
                    println(
                        it
                    )
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun listMatkul(
    listMatkul:List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
){
    LazyColumn(modifier = modifier) {
        items(
            items = listMatkul,
            itemContent = {matkul->
                CardMatkul(
                    matkul = matkul,
                    onClick = {onClick(matkul.kode)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMatkul(
    matkul : MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = matkul.kode,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Create, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = matkul.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = matkul.dosenPengampu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}