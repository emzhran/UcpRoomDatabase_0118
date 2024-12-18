package com.example.ucp2_118.dependenciesinjection

import android.content.Context
import com.example.ucp2_118.data.database.DatabaseKrs
import com.example.ucp2_118.repository.LocalRepositoryDsn
import com.example.ucp2_118.repository.LocalRepositoryMatkul
import com.example.ucp2_118.repository.RepositoryDsn
import com.example.ucp2_118.repository.RepositoryMatkul

interface InterfaceContainerApp{
    val repositoryDsn: RepositoryDsn
    val repositoryMatkul: RepositoryMatkul
}

class ContainerApp (private val context: Context): InterfaceContainerApp{
    override val repositoryDsn: RepositoryDsn by lazy {
        LocalRepositoryDsn(DatabaseKrs.getDatabase(context).DosenDao()) }

    override val repositoryMatkul: RepositoryMatkul by lazy {
        LocalRepositoryMatkul(DatabaseKrs.getDatabase(context).MatakuliahDao()) }
}