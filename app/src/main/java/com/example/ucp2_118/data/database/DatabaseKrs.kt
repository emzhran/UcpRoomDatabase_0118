package com.example.ucp2_118.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2_118.data.dao.DosenDao
import com.example.ucp2_118.data.dao.MatakuliahDao
import com.example.ucp2_118.data.entity.Dosen
import com.example.ucp2_118.data.entity.MataKuliah

@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class DatabaseKrs: RoomDatabase(){
    abstract fun DosenDao(): DosenDao
    abstract fun MatakuliahDao(): MatakuliahDao

    companion object{
        @Volatile
        private var Instance: DatabaseKrs? = null

        fun getDatabase(context: Context): DatabaseKrs{
            return (Instance?: synchronized(this){
                Room.databaseBuilder(context, DatabaseKrs::class.java,
                    "DatabaseKrs")
                    .build().also { Instance = it }
            })
        }
    }
}