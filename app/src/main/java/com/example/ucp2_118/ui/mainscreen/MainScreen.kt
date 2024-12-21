package com.example.ucp2_118.ui.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2_118.R

@Composable
fun MainMenuScreen(
    onNavigateToDosen: () -> Unit,
    onNavigateToMatakuliah: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Universitas Muhammadiyah Yogyakarta",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.umy),
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 24.dp)
            )

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
}
