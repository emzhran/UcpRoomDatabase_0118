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

    object DestinasiUpdateMatkul : AlamatNavigasi{
        override val route = "update"
        const val KODE = "kode"
        val routeWithArg ="$route/{$KODE}"
    }

    object DestinasiMainScreen : AlamatNavigasi{
        override val route = "utama"
    }

    object DestinasiHomeMatkul : AlamatNavigasi{
        override val route = "homematkul"
    }

}