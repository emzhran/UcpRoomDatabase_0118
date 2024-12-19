package com.example.ucp2_118.repository

import com.example.ucp2_118.data.dao.DosenDao
import com.example.ucp2_118.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDsn (
    private val dosenDao: DosenDao): RepositoryDsn{
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen() }

    override fun getDosen(nidn: String): Flow<Dosen> {
        return dosenDao.getDosen(nidn)
    }

}