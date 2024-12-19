package com.example.ucp2_118.ui.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_118.data.entity.Dosen
import com.example.ucp2_118.ui.customwidget.TopAppBar
import com.example.ucp2_118.ui.viewmodel.DetailDsnViewModel
import com.example.ucp2_118.ui.viewmodel.DetailUiState
import com.example.ucp2_118.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_118.ui.viewmodel.toDosenEntity

@Composable
fun DetailDsnView(
    modifier: Modifier = Modifier,
    viewModel: DetailDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: ()-> Unit = {}
){
    Scaffold(
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Detail Dosen"
            )
        },
    ) { innerpadding->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailDsn(
            modifier = modifier.padding(innerpadding),
            detailUiState = detailUiState
        )
    }
}


@Composable
fun BodyDetailDsn(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
){
    when{
        detailUiState.isLoading ->{
            Box(
                modifier = Modifier.fillMaxSize(),
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
            ) { ItemDetailDsn(
                dosen = detailUiState.detailUiEvent.toDosenEntity(),
                modifier = modifier
            ) }
        }

        detailUiState.isUiEventEmpty->{
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Data tidak ada",
                    modifier = modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailDsn(
    modifier: Modifier = Modifier,
    dosen: Dosen
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp))
        {
            ComponentDetailDsn(judul = "NIDN", isi = dosen.nidn)
            Spacer(modifier = modifier.padding(5.dp))

            ComponentDetailDsn(judul = "Nama", isi = dosen.nama)
            Spacer(modifier = Modifier.padding(5.dp))

            ComponentDetailDsn(judul = "Jenis Kelamin", isi = dosen.jenisKelamin)
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}


@Composable
fun ComponentDetailDsn(
    modifier: Modifier = Modifier,
    judul: String,
    isi: String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = isi,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}