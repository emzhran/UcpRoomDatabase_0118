package com.example.ucp2_118

import android.app.Application
import com.example.ucp2_118.dependenciesinjection.ContainerApp
import com.example.ucp2_118.dependenciesinjection.InterfaceContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp
    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}