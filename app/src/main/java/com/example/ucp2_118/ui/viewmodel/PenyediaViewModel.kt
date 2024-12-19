package com.example.ucp2_118.ui.viewmodel

import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2_118.KrsApp

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDsn
            )
        }

        initializer {
            HomeDsnViewModel(
                krsApp().containerApp.repositoryDsn
            )
        }

        initializer {
            DetailDsnViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryDsn
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)