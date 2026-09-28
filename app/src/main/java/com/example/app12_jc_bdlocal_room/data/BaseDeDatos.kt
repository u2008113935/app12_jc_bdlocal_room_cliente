package com.example.app12_jc_bdlocal_room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Persona::class], version = 1, exportSchema = false)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun dao(): PersonaDAO

    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun getDB(context: Context): BaseDeDatos {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "bd_personas"
                ).build().also { INSTANCE = it }
            }
        }
    }
}