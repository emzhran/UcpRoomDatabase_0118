package com.example.ucp2_118.navigation

interface AlamatNavigasi {
    val route : String

    object DestinasiHomeDosen : AlamatNavigasi{
        override val route = "home"
    }

    object DestinasiDetailDosen : AlamatNavigasi{
        override val route = "detail"
        const val NIDN = "nidn"
        val routeWithArg = "$route/{$NIDN}"
    }

    object DestinasiDetailMatkul : AlamatNavigasi{
        override val route = "detail"
        const val KODE = "kode"
        val routeWithArg = "$route/{$KODE}"
    }

}