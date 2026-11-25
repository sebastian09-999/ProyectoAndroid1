package com.example.prevenapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView

class DashboardUsuarioActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_usuario)

        drawerLayout = findViewById(R.id.main)
        val navViewUsuario = findViewById<NavigationView>(R.id.navViewUsuario)
        val btnMenuHamburguesa = findViewById<ImageButton>(R.id.btnMenuHamburguesa)
        val scrollDashboard = findViewById<ScrollView>(R.id.scrollDashboard)

        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val tvCantidadCitas = findViewById<TextView>(R.id.tvCantidadCitas)
        val tvCantidadFavoritos = findViewById<TextView>(R.id.tvCantidadFavoritos)
        val etBuscar = findViewById<EditText>(R.id.etBuscarDashboard)

        val btnNavInicio = findViewById<MaterialButton>(R.id.btnNavInicio)
        val btnNavServicios = findViewById<MaterialButton>(R.id.btnNavServicios)

        val cardBuscarAyuda = findViewById<MaterialCardView>(R.id.cardBuscarAyuda)
        val cardProximasCitas = findViewById<MaterialCardView>(R.id.cardProximasCitas)
        val cardFavoritos = findViewById<MaterialCardView>(R.id.cardFavoritos)
        val cardAprender = findViewById<MaterialCardView>(R.id.cardAprender)
        val cardAsistente = findViewById<MaterialCardView>(R.id.cardAsistente)

        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO")
        if (!nombreUsuario.isNullOrBlank()) {
            tvBienvenida.text = "¡Hola, $nombreUsuario!"
            val headerView = navViewUsuario.getHeaderView(0)
            headerView.findViewById<TextView>(R.id.tvDrawerNombreUsuario).text = "¡Hola, $nombreUsuario!"
        }

        tvCantidadCitas.text = "1"
        tvCantidadFavoritos.text = "5"

        // Abrir menú lateral al tocar el icono de hamburguesa
        btnMenuHamburguesa.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Marcar "Inicio" por defecto
        navViewUsuario.setCheckedItem(R.id.nav_usuario_inicio)

        val abrirServicios = {
            startActivity(Intent(this@DashboardUsuarioActivity, ServiciosActivity::class.java))
        }

        // Eventos del menú lateral vertical izquierdo
        navViewUsuario.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_usuario_inicio -> {
                    scrollDashboard.smoothScrollTo(0, 0)
                }
                R.id.nav_usuario_servicios -> {
                    abrirServicios()
                }
                R.id.nav_usuario_citas -> {
                    Toast.makeText(this, "Módulo Mis Citas (pendiente)", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_usuario_recursos -> {
                    Toast.makeText(this, "Módulo Recursos (pendiente)", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_usuario_asistente -> {
                    Toast.makeText(this, "Módulo Asistente Virtual (pendiente)", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_usuario_perfil -> {
                    Toast.makeText(this, "Módulo Mi Perfil (pendiente)", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_usuario_cerrar_sesion -> {
                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Barra inferior y tarjetas rápidas
        btnNavInicio.setOnClickListener { scrollDashboard.smoothScrollTo(0, 0) }
        btnNavServicios.setOnClickListener { abrirServicios() }
        cardBuscarAyuda.setOnClickListener { abrirServicios() }

        cardProximasCitas.setOnClickListener {
            Toast.makeText(this, "Módulo Mis Citas (pendiente)", Toast.LENGTH_SHORT).show()
        }
        cardAprender.setOnClickListener {
            Toast.makeText(this, "Módulo Aprender / Recursos (pendiente)", Toast.LENGTH_SHORT).show()
        }
        cardAsistente.setOnClickListener {
            Toast.makeText(this, "Módulo Asistente Virtual (pendiente)", Toast.LENGTH_SHORT).show()
        }
        cardFavoritos.setOnClickListener {
            Toast.makeText(this, "Módulo Favoritos (pendiente)", Toast.LENGTH_SHORT).show()
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

        // Cerrar el menú lateral primero si el usuario presiona "Atrás"
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}