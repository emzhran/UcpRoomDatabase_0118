package com.example.ucp2_118.repository

import com.example.ucp2_118.data.entity.Dosen
import kotlinx.coroutines.flow.Flow


interface RepositoryDsn {
    suspend fun insertDosen(dosen: Dosen)
    fun getAllDosen(): Flow<List<Dosen>>
    fun getDosen(nidn : String): Flow<Dosen>
}