package com.example.ucp2_118.repository

import com.example.ucp2_118.data.dao.MatakuliahDao
import com.example.ucp2_118.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMatkul (
    private val matakuliahDao: MatakuliahDao): RepositoryMatkul{
    override suspend fun insertMatakuliah(mataKuliah: MataKuliah) {
        matakuliahDao.insertMatakuliah(mataKuliah)
    }

    override suspend fun deleteMatakuliah(mataKuliah: MataKuliah) {
        matakuliahDao.deleteMatakuliah(mataKuliah)
    }

    override suspend fun updateMatakuliah(mataKuliah: MataKuliah) {
        matakuliahDao.updateMatakuliah(mataKuliah)
    }

    override fun getMatakuliah(mataKuliah: MataKuliah): Flow<List<MataKuliah>> {
        return matakuliahDao.getAllMatakuliah()
    }
    
    }