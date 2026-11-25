package com.example.prevenapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ServiciosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)

        val btnVolverInicio = findViewById<MaterialButton>(R.id.btnVolverInicio)
        val btnSolicitarOrientacion = findViewById<MaterialButton>(R.id.btnSolicitarOrientacion)
        val btnSolicitarApoyo = findViewById<MaterialButton>(R.id.btnSolicitarApoyo)

        val filtro = intent.getStringExtra("FILTRO_SERVICIO")
        if (!filtro.isNullOrBlank()) {
            Toast.makeText(this, "Buscando: $filtro", Toast.LENGTH_SHORT).show()
        }

        btnVolverInicio.setOnClickListener {
            val intent = Intent(this@ServiciosActivity, DashboardUsuarioActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        btnSolicitarOrientacion.setOnClickListener {
            Toast.makeText(this, "Orientación y Bienestar seleccionada", Toast.LENGTH_SHORT).show()
        }

        btnSolicitarApoyo.setOnClickListener {
            Toast.makeText(this, "Líneas y Centros de Apoyo seleccionados", Toast.LENGTH_SHORT).show()
        }
    }
}
