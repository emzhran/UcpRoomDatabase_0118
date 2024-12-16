package com.example.ucp2_118.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2_118.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface MatakuliahDao {
    @Query("SELECT * FROM matakuliah ORDER BY kode ASC")
    fun getAllMatakuliah(): Flow<List<MataKuliah>>

    @Insert
    suspend fun insertMatakuliah(mataKuliah: MataKuliah)

    @Delete
    suspend fun deleteMatakuliah(mataKuliah: MataKuliah)

    @Update
    suspend fun updateMatakuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM  matakuliah where kode = :kode")
    fun getMatakuliah(kode : String): Flow<MataKuliah>


}