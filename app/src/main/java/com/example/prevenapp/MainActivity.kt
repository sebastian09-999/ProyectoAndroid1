package com.example.prevenapp

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    private lateinit var btnFiltroTodos: MaterialButton
    private lateinit var btnFiltroArticulos: MaterialButton
    private lateinit var btnFiltroGuias: MaterialButton
    private lateinit var btnFiltroInfografias: MaterialButton

    private lateinit var cardGuia: MaterialCardView
    private lateinit var cardVideo: MaterialCardView
    private lateinit var cardArticulo: MaterialCardView
    private lateinit var cardInfografia: MaterialCardView

    private lateinit var tvHeaderNombre: TextView
    private lateinit var etBuscarRecurso: EditText

    // Filter state
    private var filtroTipo = "Todos"
    private var filtroCategoria = "Todas las categorías"
    private var filtroBusqueda = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recursosprofesional)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Header & Search
        tvHeaderNombre = findViewById(R.id.tvHeaderNombre)
        etBuscarRecurso = findViewById(R.id.etBuscarRecurso)

        // Initialize Filter Buttons
        btnFiltroTodos = findViewById(R.id.btnFiltroTodos)
        btnFiltroArticulos = findViewById(R.id.btnFiltroArticulos)
        btnFiltroGuias = findViewById(R.id.btnFiltroGuias)
        btnFiltroInfografias = findViewById(R.id.btnFiltroInfografias)

        // Initialize Resource Cards
        cardGuia = findViewById(R.id.cardGuia)
        cardVideo = findViewById(R.id.cardVideo)
        cardArticulo = findViewById(R.id.cardArticulo)
        cardInfografia = findViewById(R.id.cardInfografia)

        // Setup Spinner categories
        val spinner = findViewById<Spinner>(R.id.spCategorias)
        val categorias = arrayOf("Todas las categorías", "Salud Sexual", "Anticoncepción", "ITS y Prevención", "Salud Mental")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)
        spinner.adapter = adapter

        // Set initial filter state
        seleccionarBotonFiltro("Todos")
        aplicarFiltros()

        // Filter button click listeners
        btnFiltroTodos.setOnClickListener {
            filtroTipo = "Todos"
            seleccionarBotonFiltro("Todos")
            aplicarFiltros()
            Toast.makeText(this, "Mostrando todos los recursos", Toast.LENGTH_SHORT).show()
        }

        btnFiltroArticulos.setOnClickListener {
            filtroTipo = "Articulos"
            seleccionarBotonFiltro("Articulos")
            aplicarFiltros()
            Toast.makeText(this, "Filtrando por Artículos", Toast.LENGTH_SHORT).show()
        }

        btnFiltroGuias.setOnClickListener {
            filtroTipo = "Guias"
            seleccionarBotonFiltro("Guias")
            aplicarFiltros()
            Toast.makeText(this, "Filtrando por Guías PDF", Toast.LENGTH_SHORT).show()
        }

        btnFiltroInfografias.setOnClickListener {
            filtroTipo = "Infografias"
            seleccionarBotonFiltro("Infografias")
            aplicarFiltros()
            Toast.makeText(this, "Filtrando por Infografías", Toast.LENGTH_SHORT).show()
        }

        // Live Search Filter
        etBuscarRecurso.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtroBusqueda = s.toString()
                aplicarFiltros()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Spinner selection listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtroCategoria = categorias[position]
                aplicarFiltros()
                if (position > 0) {
                    Toast.makeText(this@MainActivity, "Filtrando por: ${categorias[position]}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Action buttons on cards
        findViewById<View>(R.id.btnDescargarGuia).setOnClickListener {
            Toast.makeText(this, "Descargando Guía Completa de Métodos Anticonceptivos...", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.btnVerVideo).setOnClickListener {
            Toast.makeText(this, "Reproduciendo video de consentimiento...", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.btnAbrirArticulo).setOnClickListener {
            Toast.makeText(this, "Abriendo artículo sobre ITS...", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.btnDescargarInfografia).setOnClickListener {
            Toast.makeText(this, "Descargando infografía de Rutas de Atención...", Toast.LENGTH_SHORT).show()
        }

        // Navigation to Profile
        findViewById<View>(R.id.btnIrPerfil).setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Load updated user name from SharedPreferences
        val prefs = getSharedPreferences("PerfilPrefs", MODE_PRIVATE)
        val nombre = prefs.getString("nombre", "carlos")
        val apellido = prefs.getString("apellido", "perez")
        tvHeaderNombre.text = "$nombre $apellido"
    }

    private fun aplicarFiltros() {
        val q = filtroBusqueda.trim().lowercase()

        cardGuia.visibility = if (cumpleFiltros(
                tipoCard = "Guias",
                categoriasCard = listOf("Todas las categorías", "Anticoncepción", "Salud Sexual"),
                textoCard = "guia completa de metodos anticonceptivos eficacia uso efectos secundarios pdf",
                query = q
            )) View.VISIBLE else View.GONE

        cardVideo.visibility = if (cumpleFiltros(
                tipoCard = "Video",
                categoriasCard = listOf("Todas las categorías", "Salud Mental", "Salud Sexual"),
                textoCard = "como hablar de consentimiento con adolescentes video estrategias comunicacion empatia",
                query = q
            )) View.VISIBLE else View.GONE

        cardArticulo.visibility = if (cumpleFiltros(
                tipoCard = "Articulos",
                categoriasCard = listOf("Todas las categorías", "ITS y Prevención", "Salud Sexual"),
                textoCard = "mitos y realidades sobre las its articulo desmentir creencias jovenes",
                query = q
            )) View.VISIBLE else View.GONE

        cardInfografia.visibility = if (cumpleFiltros(
                tipoCard = "Infografias",
                categoriasCard = listOf("Todas las categorías", "Salud Mental", "ITS y Prevención"),
                textoCard = "rutas de atencion y lineas de ayuda infografia mapa visual contactos emergencia centros salud",
                query = q
            )) View.VISIBLE else View.GONE
    }

    private fun cumpleFiltros(
        tipoCard: String,
        categoriasCard: List<String>,
        textoCard: String,
        query: String
    ): Boolean {
        // 1. Tipo filter check
        if (filtroTipo != "Todos" && filtroTipo != tipoCard) {
            return false
        }

        // 2. Categoria filter check
        if (filtroCategoria != "Todas las categorías" && !categoriasCard.contains(filtroCategoria)) {
            return false
        }

        // 3. Search query check
        if (query.isNotEmpty() && !textoCard.contains(query)) {
            return false
        }

        return true
    }

    private fun seleccionarBotonFiltro(tipo: String) {
        setButtonInactive(btnFiltroTodos)
        setButtonInactive(btnFiltroArticulos)
        setButtonInactive(btnFiltroGuias)
        setButtonInactive(btnFiltroInfografias)

        when (tipo) {
            "Todos" -> setButtonActive(btnFiltroTodos)
            "Articulos" -> setButtonActive(btnFiltroArticulos)
            "Guias" -> setButtonActive(btnFiltroGuias)
            "Infografias" -> setButtonActive(btnFiltroInfografias)
        }
    }

    private fun setButtonActive(button: MaterialButton) {
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_blue))
        button.setTextColor(ContextCompat.getColor(this, R.color.white))
        button.strokeWidth = 0
    }

    private fun setButtonInactive(button: MaterialButton) {
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.surface_white))
        button.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
        button.strokeColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.border_slate))
        button.strokeWidth = 2
    }
}
