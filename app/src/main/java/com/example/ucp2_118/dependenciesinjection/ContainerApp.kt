package com.example.ucp2_118.dependenciesinjection

import com.example.ucp2_118.repository.RepositoryDsn
import com.example.ucp2_118.repository.RepositoryMatkul

interface InterfaceContainerApp{
    val repositoryDsn: RepositoryDsn
    val repositoryMatkul: RepositoryMatkul
}
