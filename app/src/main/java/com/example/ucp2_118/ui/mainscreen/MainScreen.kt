package com.example.ucp2_118.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(
    onNavigateToDosen: () -> Unit,
    onNavigateToMatakuliah: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onNavigateToDosen() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Dosen")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNavigateToMatakuliah() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Matakuliah")
        }
    }
}
