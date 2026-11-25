package com.example.prevenapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class activity_usuario_perfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_perfil)

        val btnVolver = findViewById<Button>(R.id.btnVolverPerfil)
        val tvAvisoAnonimo = findViewById<TextView>(R.id.tvAvisoAnonimo)
        val etNombres = findViewById<EditText>(R.id.etNombres)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etFactorRh = findViewById<EditText>(R.id.etFactorRh)
        val etContactoNombre = findViewById<EditText>(R.id.etContactoNombre)
        val etContactoTelefono = findViewById<EditText>(R.id.etContactoTelefono)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPerfil)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        btnVolver.setOnClickListener {
            finish()
        }

        val esAnonimo = intent.getBooleanExtra("ES_ANONIMO", false)
        if (esAnonimo) {
            tvAvisoAnonimo.visibility = View.VISIBLE
            btnGuardar.isEnabled = false
        }

        etNombres.setText("Camila")
        etApellidos.setText("Rodríguez")
        etCorreo.setText("camila.r@ejemplo.com")
        etTelefono.setText("3101234567")
        etEdad.setText("15")
        etFactorRh.setText("O+")

        btnGuardar.setOnClickListener {
            val nombres = etNombres.text.toString().trim()
            val apellidos = etApellidos.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val edadTexto = etEdad.text.toString().trim()
            val factorRh = etFactorRh.text.toString().trim()
            val contactoTel = etContactoTelefono.text.toString().trim()

            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() ||
                telefono.isEmpty() || edadTexto.isEmpty() || factorRh.isEmpty()
            ) {
                Toast.makeText(this, "Completa todos los campos personales obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = edadTexto.toIntOrNull() ?: 0
            if (edad < 13 || edad > 17) {
                Toast.makeText(this, "La edad del usuario debe estar entre 13 y 17 años", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contactoTel.isEmpty()) {
                Toast.makeText(this, "Configura al menos un teléfono de emergencia (RF-NU-005)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Datos de perfil y contacto de emergencia guardados", Toast.LENGTH_SHORT).show()
        }

        btnCerrarSesion.setOnClickListener {
            Toast.makeText(this, "Sesión finalizada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}