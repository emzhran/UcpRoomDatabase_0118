package com.example.ucp2_118.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2_118.ui.view.dosen.DestinasiInsertDosen
import com.example.ucp2_118.ui.view.dosen.DetailDsnView
import com.example.ucp2_118.ui.view.dosen.HomeDsnView
import com.example.ucp2_118.ui.view.dosen.InsertDsnView
import com.example.ucp2_118.ui.viewmodel.DetailDsnViewModel
import com.example.ucp2_118.ui.viewmodel.HomeDsnViewModel
import com.example.ucp2_118.ui.viewmodel.HomeUiState

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination = AlamatNavigasi.DestinasiHomeDosen.route) {
        composable(
            route = AlamatNavigasi.DestinasiHomeDosen.route
        ) {
            HomeDsnView(
                onAddDsn = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsertDosen.route
        ) {
            InsertDsnView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(AlamatNavigasi.DestinasiDetailDosen.routeWithArg,
            arguments =  listOf(
                navArgument(AlamatNavigasi.DestinasiDetailDosen.NIDN){
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(AlamatNavigasi.DestinasiDetailDosen.NIDN)
            nidn?.let { nidn->
                DetailDsnView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }
    }
}