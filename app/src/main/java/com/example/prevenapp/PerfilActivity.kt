package com.example.prevenapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfilprofesional)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNombres = findViewById<EditText>(R.id.etNombres)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etEspecialidad = findViewById<EditText>(R.id.etEspecialidad)

        val tvNombreResumen = findViewById<TextView>(R.id.tvNombreResumen)
        val tvEspecialidadResumen = findViewById<TextView>(R.id.tvEspecialidadResumen)

        val btnGuardarCambios = findViewById<Button>(R.id.btnGuardarCambios)
        val btnSeleccionarFoto = findViewById<Button>(R.id.btnSeleccionarFoto)
        val btnEditarInfo = findViewById<View>(R.id.btnEditarInfo)

        // Load saved profile data from SharedPreferences
        val prefs = getSharedPreferences("PerfilPrefs", MODE_PRIVATE)
        val nombreGuardado = prefs.getString("nombre", "Carlos")
        val apellidoGuardado = prefs.getString("apellido", "Perez")
        val correoGuardado = prefs.getString("correo", "carlos@gmail.com")
        val telefonoGuardado = prefs.getString("telefono", "")
        val especialidadGuardada = prefs.getString("especialidad", "Medicina")

        etNombres.setText(nombreGuardado)
        etApellidos.setText(apellidoGuardado)
        etCorreo.setText(correoGuardado)
        etTelefono.setText(telefonoGuardado)
        etEspecialidad.setText(especialidadGuardada)

        tvNombreResumen.text = "$nombreGuardado $apellidoGuardado"
        tvEspecialidadResumen.text = especialidadGuardada

        btnEditarInfo?.setOnClickListener {
            etNombres.requestFocus()
            Toast.makeText(this, "Puedes editar tu información personal abajo", Toast.LENGTH_SHORT).show()
        }

        btnSeleccionarFoto.setOnClickListener {
            Toast.makeText(this, "Seleccionar foto de perfil próximamente", Toast.LENGTH_SHORT).show()
        }

        btnGuardarCambios.setOnClickListener {
            val nuevoNombre = etNombres.text.toString().trim()
            val nuevoApellido = etApellidos.text.toString().trim()
            val nuevoCorreo = etCorreo.text.toString().trim()
            val nuevoTelefono = etTelefono.text.toString().trim()

            if (nuevoNombre.isEmpty()) {
                etNombres.error = "Ingresa tu nombre"
                return@setOnClickListener
            }

            if (nuevoApellido.isEmpty()) {
                etApellidos.error = "Ingresa tu apellido"
                return@setOnClickListener
            }

            // Save to SharedPreferences
            prefs.edit()
                .putString("nombre", nuevoNombre)
                .putString("apellido", nuevoApellido)
                .putString("correo", nuevoCorreo)
                .putString("telefono", nuevoTelefono)
                .apply()

            // Update UI live
            tvNombreResumen.text = "$nuevoNombre $nuevoApellido"

            Toast.makeText(this, "¡Cambios guardados exitosamente!", Toast.LENGTH_SHORT).show()
        }
    }
}
