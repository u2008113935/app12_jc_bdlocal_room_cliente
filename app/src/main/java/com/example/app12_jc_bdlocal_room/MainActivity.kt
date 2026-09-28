package com.example.app12_jc_bdlocal_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.app12_jc_bdlocal_room.data.BaseDeDatos
import com.example.app12_jc_bdlocal_room.data.Persona
import com.example.app12_jc_bdlocal_room.ui.theme.App12_jc_bdlocal_roomTheme
import com.example.app12_jc_bdlocal_room.viewmodel.PersonaViewModel


class MainActivity : ComponentActivity() {

    private val vm: PersonaViewModel by viewModels {
        viewModelFactory {
            initializer {
                val db = BaseDeDatos.getDB(this@MainActivity)
                PersonaViewModel(db.dao())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App12_jc_bdlocal_roomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaPrincipal(
                        vm = vm,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(vm: PersonaViewModel, modifier: Modifier = Modifier) {
    val lista by vm.lista.collectAsState(initial = emptyList())
    var txtNombre by remember { mutableStateOf("") }
    var txtApellido by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {

        OutlinedTextField(
            value = txtNombre,
            onValueChange = { txtNombre = it },
            label = { Text("Nombre") }
        )
        OutlinedTextField(
            value = txtApellido,
            onValueChange = { txtApellido = it },
            label = { Text("Apellido") }
        )

        Button(onClick = {
            vm.insertar(txtNombre, txtApellido)
            txtNombre = ""
            txtApellido = ""
        }) {
            Text("Guardar")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(lista, key = { it.id }) { p ->
                ItemPersona(p, vm)
            }
        }
    }
}

@Composable
fun ItemPersona(p: Persona, vm: PersonaViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(p.nombre) }
    var apellido by remember { mutableStateOf(p.apellido) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Editar persona") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") }
                    )
                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        label = { Text("Apellido") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    vm.actualizar(p.copy(nombre = nombre, apellido = apellido))
                    showDialog = false
                }) { Text("Guardar") }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("${p.nombre} ${p.apellido}", modifier = Modifier.weight(1f))

        IconButton(onClick = { showDialog = true }) {
            Icon(Icons.Default.Edit, contentDescription = "Editar")
        }
        IconButton(onClick = { vm.eliminar(p) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}