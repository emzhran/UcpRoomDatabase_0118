package com.example.ucp2_118.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2_118.ui.mainscreen.MainMenuScreen
import com.example.ucp2_118.ui.view.dosen.DestinasiInsertDosen
import com.example.ucp2_118.ui.view.dosen.HomeDsnView
import com.example.ucp2_118.ui.view.dosen.InsertDsnView
import com.example.ucp2_118.ui.view.matakuliah.DestinasiInsertMatkul
import com.example.ucp2_118.ui.view.matakuliah.DetailMatkulView
import com.example.ucp2_118.ui.view.matakuliah.HomeMatkulView
import com.example.ucp2_118.ui.view.matakuliah.InsertMatkulView
import com.example.ucp2_118.ui.view.matakuliah.updateMatkulView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = AlamatNavigasi.DestinasiMainScreen.route) {
        // Main Screen
        composable(
            route = AlamatNavigasi.DestinasiMainScreen.route
        ) {
            MainMenuScreen(
                onNavigateToDosen = {
                    navController.navigate(AlamatNavigasi.DestinasiHomeDosen.route)
                },
                onNavigateToMatakuliah = {
                    navController.navigate(AlamatNavigasi.DestinasiHomeMatkul.route)
                },
                modifier = modifier
            )
        }

        // Home Dosen
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

        // Home Matakuliah
        composable(
            route = AlamatNavigasi.DestinasiHomeMatkul.route
        ) {
            HomeMatkulView(
                onDetailClick = {kode->
                    navController.navigate("${AlamatNavigasi.DestinasiDetailMatkul.route}/$kode")
                    println(
                        "Pengelola Halaman : kode = $kode"
                    )
                },
                onAddMatkul = {
                    navController.navigate(DestinasiInsertMatkul.route)
                },
                modifier = modifier
            )
        }

        //Insert Matakuliah
        composable(
            route = DestinasiInsertMatkul.route
        ) {
            InsertMatkulView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = AlamatNavigasi.DestinasiDetailMatkul.routeWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiDetailMatkul.KODE){
                    type = NavType.StringType
                }
            )
        ) {
            DetailMatkulView(
                onBack = {navController.popBackStack()},
                onEdit = {navController.navigate("${AlamatNavigasi.DestinasiUpdateMatkul.route}/$it")},
                modifier = modifier,
                onDeleteClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = AlamatNavigasi.DestinasiUpdateMatkul.routeWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiUpdateMatkul.KODE){
                    type = NavType.StringType
                }
            )
        ) {
            updateMatkulView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}
