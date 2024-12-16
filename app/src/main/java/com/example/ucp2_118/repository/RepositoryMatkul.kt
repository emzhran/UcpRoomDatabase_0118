package com.example.ucp2_118.repository

import com.example.ucp2_118.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMatkul {
    suspend fun insertMatakuliah(mataKuliah: MataKuliah)
    fun getAllMatakuliah(mataKuliah: MataKuliah)
    fun getMatakuliah(mataKuliah: MataKuliah): Flow<List<MataKuliah>>
    suspend fun deleteMatakuliah(mataKuliah: MataKuliah)
    suspend fun updateMatakuliah(mataKuliah: MataKuliah)
}