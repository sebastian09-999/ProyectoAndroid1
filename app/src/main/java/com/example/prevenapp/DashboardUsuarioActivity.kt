package com.example.prevenapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class DashboardUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_usuario)

        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val tvCantidadCitas = findViewById<TextView>(R.id.tvCantidadCitas)
        val tvCantidadFavoritos = findViewById<TextView>(R.id.tvCantidadFavoritos)
        val etBuscar = findViewById<EditText>(R.id.etBuscarDashboard)

        // Botones de tu módulo (Servicios)
        val btnMenuServicios = findViewById<MaterialButton>(R.id.btnMenuServicios)
        val btnNavServicios = findViewById<MaterialButton>(R.id.btnNavServicios)
        val cardBuscarAyuda = findViewById<MaterialCardView>(R.id.cardBuscarAyuda)

        // Botones para tus compañeros
        val btnMenuMisCitas = findViewById<MaterialButton>(R.id.btnMenuMisCitas)
        val btnMenuRecursos = findViewById<MaterialButton>(R.id.btnMenuRecursos)
        val btnMenuAsistente = findViewById<MaterialButton>(R.id.btnMenuAsistente)
        val btnMenuPerfil = findViewById<MaterialButton>(R.id.btnMenuPerfil)
        val btnCerrarSesion = findViewById<MaterialButton>(R.id.btnCerrarSesion)
        val cardProximasCitas = findViewById<MaterialCardView>(R.id.cardProximasCitas)
        val cardFavoritos = findViewById<MaterialCardView>(R.id.cardFavoritos)
        val cardAprender = findViewById<MaterialCardView>(R.id.cardAprender)
        val cardAsistente = findViewById<MaterialCardView>(R.id.cardAsistente)

        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO")
        if (!nombreUsuario.isNullOrBlank()) {
            tvBienvenida.text = "¡Hola, $nombreUsuario!"
        }

        tvCantidadCitas.text = "1"
        tvCantidadFavoritos.text = "5"

        // --- NAVEGACIÓN A TU MÓDULO DE SERVICIOS ---
        val abrirServicios = {
            startActivity(Intent(this@DashboardUsuarioActivity, ServiciosActivity::class.java))
        }
        btnMenuServicios.setOnClickListener { abrirServicios() }
        btnNavServicios.setOnClickListener { abrirServicios() }
        cardBuscarAyuda.setOnClickListener { abrirServicios() }

        // --- MÓDULOS DE TUS COMPAÑEROS (reemplazar Toast por startActivity) ---
        btnMenuMisCitas.setOnClickListener {
            Toast.makeText(this, "Módulo Mis Citas (pendiente)", Toast.LENGTH_SHORT).show()
        }
        cardProximasCitas.setOnClickListener {
            Toast.makeText(this, "Módulo Mis Citas (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnMenuRecursos.setOnClickListener {
            Toast.makeText(this, "Módulo Recursos (pendiente)", Toast.LENGTH_SHORT).show()
        }
        cardAprender.setOnClickListener {
            Toast.makeText(this, "Módulo Aprender / Recursos (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnMenuAsistente.setOnClickListener {
            Toast.makeText(this, "Módulo Asistente Virtual (pendiente)", Toast.LENGTH_SHORT).show()
        }
        cardAsistente.setOnClickListener {
            Toast.makeText(this, "Módulo Asistente Virtual (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnMenuPerfil.setOnClickListener {
            Toast.makeText(this, "Módulo Mi Perfil (pendiente)", Toast.LENGTH_SHORT).show()
        }

        cardFavoritos.setOnClickListener {
            Toast.makeText(this, "Módulo Favoritos (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this@DashboardUsuarioActivity, WelcomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        // Buscador hacia Servicios
        etBuscar.setOnEditorActionListener { _, _, _ ->
            val consulta = etBuscar.text.toString().trim()
            if (consulta.isNotEmpty()) {
                val intent = Intent(this@DashboardUsuarioActivity, ServiciosActivity::class.java)
                intent.putExtra("FILTRO_SERVICIO", consulta)
                startActivity(intent)
            }
            true
        }
    }
}
