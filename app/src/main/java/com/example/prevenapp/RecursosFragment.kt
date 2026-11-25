package com.example.prevenapp

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class RecursosFragment : Fragment() {

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

    private var filtroTipo = "Todos"
    private var filtroCategoria = "Todas las categorías"
    private var filtroBusqueda = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_recursosprofesional, container, false)

        // Initialize Header & Search
        tvHeaderNombre = view.findViewById(R.id.tvHeaderNombre)
        etBuscarRecurso = view.findViewById(R.id.etBuscarRecurso)

        // Initialize Filter Buttons
        btnFiltroTodos = view.findViewById(R.id.btnFiltroTodos)
        btnFiltroArticulos = view.findViewById(R.id.btnFiltroArticulos)
        btnFiltroGuias = view.findViewById(R.id.btnFiltroGuias)
        btnFiltroInfografias = view.findViewById(R.id.btnFiltroInfografias)

        // Initialize Resource Cards
        cardGuia = view.findViewById(R.id.cardGuia)
        cardVideo = view.findViewById(R.id.cardVideo)
        cardArticulo = view.findViewById(R.id.cardArticulo)
        cardInfografia = view.findViewById(R.id.cardInfografia)

        // Setup Spinner
        val spinner = view.findViewById<Spinner>(R.id.spCategorias)
        val categorias = arrayOf("Todas las categorías", "Salud Sexual", "Anticoncepción", "ITS y Prevención", "Salud Mental")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categorias)
        spinner.adapter = adapter

        seleccionarBotonFiltro("Todos")
        aplicarFiltros()

        // Listeners
        btnFiltroTodos.setOnClickListener { actualizarTipo("Todos") }
        btnFiltroArticulos.setOnClickListener { actualizarTipo("Articulos") }
        btnFiltroGuias.setOnClickListener { actualizarTipo("Guias") }
        btnFiltroInfografias.setOnClickListener { actualizarTipo("Infografias") }

        etBuscarRecurso.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtroBusqueda = s.toString()
                aplicarFiltros()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                filtroCategoria = categorias[position]
                aplicarFiltros()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        view.findViewById<View>(R.id.btnIrPerfil).setOnClickListener {
            startActivity(Intent(requireContext(), PerfilActivity::class.java))
        }

        return view
    }

    private fun actualizarTipo(tipo: String) {
        filtroTipo = tipo
        seleccionarBotonFiltro(tipo)
        aplicarFiltros()
    }

    override fun onResume() {
        super.onResume()
        val prefs = requireContext().getSharedPreferences("PerfilPrefs", Context.MODE_PRIVATE)
        val nombre = prefs.getString("nombre", "carlos")
        val apellido = prefs.getString("apellido", "perez")
        tvHeaderNombre.text = "$nombre $apellido"
    }

    private fun aplicarFiltros() {
        val q = filtroBusqueda.trim().lowercase()
        cardGuia.visibility = if (cumpleFiltros("Guias", listOf("Todas las categorías", "Anticoncepción", "Salud Sexual"), "guia completa de metodos anticonceptivos eficacia uso efectos secundarios pdf", q)) View.VISIBLE else View.GONE
        cardVideo.visibility = if (cumpleFiltros("Video", listOf("Todas las categorías", "Salud Mental", "Salud Sexual"), "como hablar de consentimiento con adolescentes video estrategias comunicacion empatia", q)) View.VISIBLE else View.GONE
        cardArticulo.visibility = if (cumpleFiltros("Articulos", listOf("Todas las categorías", "ITS y Prevención", "Salud Sexual"), "mitos y realidades sobre las its articulo desmentir creencias jovenes", q)) View.VISIBLE else View.GONE
        cardInfografia.visibility = if (cumpleFiltros("Infografias", listOf("Todas las categorías", "Salud Mental", "ITS y Prevención"), "rutas de atencion y lineas de ayuda infografia mapa visual contactos emergencia centros salud", q)) View.VISIBLE else View.GONE
    }

    private fun cumpleFiltros(tipoCard: String, categoriasCard: List<String>, textoCard: String, query: String): Boolean {
        if (filtroTipo != "Todos" && filtroTipo != tipoCard) return false
        if (filtroCategoria != "Todas las categorías" && !categoriasCard.contains(filtroCategoria)) return false
        if (query.isNotEmpty() && !textoCard.contains(query)) return false
        return true
    }

    private fun seleccionarBotonFiltro(tipo: String) {
        listOf(btnFiltroTodos, btnFiltroArticulos, btnFiltroGuias, btnFiltroInfografias).forEach { setButtonInactive(it) }
        when (tipo) {
            "Todos" -> setButtonActive(btnFiltroTodos)
            "Articulos" -> setButtonActive(btnFiltroArticulos)
            "Guias" -> setButtonActive(btnFiltroGuias)
            "Infografias" -> setButtonActive(btnFiltroInfografias)
        }
    }

    private fun setButtonActive(button: MaterialButton) {
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.primary_blue))
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        button.strokeWidth = 0
    }

    private fun setButtonInactive(button: MaterialButton) {
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.surface_white))
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        button.strokeColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.border_slate))
        button.strokeWidth = 2
    }
}
