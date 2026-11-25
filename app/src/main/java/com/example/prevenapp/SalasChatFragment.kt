package com.example.prevenapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class SalasChatFragment : Fragment(R.layout.fragment_salas_chat) {

    private lateinit var rvSalas: RecyclerView
    private lateinit var adapter: SalaChatAdapter
    private val listaSalas = mutableListOf<SalaChat>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSalas = view.findViewById(R.id.rvSalas)
        val btnCrear = view.findViewById<MaterialButton>(R.id.btnCrearSala)

        cargarDatosDePrueba()

        adapter = SalaChatAdapter(listaSalas) { sala ->
            // Al hacer clic en una sala, navegamos al ChatDetalleFragment
            val fragment = ChatDetalleFragment.newInstance(sala.tema)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Permite volver atrás
                .commit()
        }

        rvSalas.layoutManager = LinearLayoutManager(context)
        rvSalas.adapter = adapter

        btnCrear.setOnClickListener {
            Toast.makeText(context, "Crear sala (Próximamente)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarDatosDePrueba() {
        listaSalas.add(SalaChat(1, "Consejos de Nutrición", "Hablemos de alimentación", "Dr. Carlos"))
        listaSalas.add(SalaChat(2, "Dudas sobre ITS", "Espacio de orientación", "Dra. María"))
        listaSalas.add(SalaChat(3, "Apoyo Emocional", "Un espacio para sentirnos bien", "Psic. Ana"))
    }
}
