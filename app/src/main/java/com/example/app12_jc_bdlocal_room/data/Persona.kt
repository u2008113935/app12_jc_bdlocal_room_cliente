package com.example.app12_jc_bdlocal_room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class Persona (
    @PrimaryKey(autoGenerate = true)
            val id: Int = 0,
            val nombre: String,
            val apellido: String
)