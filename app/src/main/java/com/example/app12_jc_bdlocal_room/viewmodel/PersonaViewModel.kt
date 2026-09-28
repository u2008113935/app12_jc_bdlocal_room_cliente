package com.example.app12_jc_bdlocal_room.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app12_jc_bdlocal_room.data.Persona
import com.example.app12_jc_bdlocal_room.data.PersonaDAO
import kotlinx.coroutines.launch

class PersonaViewModel (private val dao: PersonaDAO) : ViewModel() {
    val lista = dao.obtenerTodos()

    fun insertar(nombre: String, apellido: String) = viewModelScope.launch {
        dao.insertar(Persona(nombre = nombre, apellido = apellido))
    }

    fun actualizar(p: Persona) = viewModelScope.launch {
        dao.actualizar(p)
    }

    fun eliminar(p: Persona) = viewModelScope.launch {
        dao.eliminar(p)
    }
}