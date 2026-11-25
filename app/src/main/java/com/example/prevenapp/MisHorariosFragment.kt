package com.example.prevenapp

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MisHorariosFragment : Fragment(R.layout.fragment_mis_horarios) {

    private lateinit var rvHorarios: RecyclerView
    private lateinit var emptyStateLayout: LinearLayout
    private lateinit var adapter: HorarioAdapter
    private val listaHorarios = mutableListOf<Horario>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHorarios = view.findViewById(R.id.rvHorarios)
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout)
        val btnNuevo = view.findViewById<MaterialButton>(R.id.btnNuevoHorario)

        setupRecyclerView()
        cargarDatosDePrueba() // Simulamos datos

        // Acción al dar clic en "Nuevo Horario"
        btnNuevo.setOnClickListener {
            Toast.makeText(context, "Ir a pantalla de Crear Horario", Toast.LENGTH_SHORT).show()
            // Aquí en el futuro harás: findNavController().navigate(R.id.action_misHorarios_to_crearHorario)
        }
    }

    private fun setupRecyclerView() {
        adapter = HorarioAdapter(
            listaHorarios,
            onEditClick = { horario ->
                Toast.makeText(context, "Editar ${horario.dia}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { horario ->
                Toast.makeText(context, "Eliminar ${horario.dia}", Toast.LENGTH_SHORT).show()
                // Aquí luego agregarás la lógica para borrar de la BD
            }
        )
        rvHorarios.layoutManager = LinearLayoutManager(context)
        rvHorarios.adapter = adapter
    }

    private fun cargarDatosDePrueba() {
        // Simulamos que ya tienes horarios creados en la base de datos
        listaHorarios.add(Horario(1, "Lunes", "Vigencia: 19 Nov 2026 - 30 Nov 2026", "08:00 AM - 05:00 PM", "Máx. 5 citas/día"))
        listaHorarios.add(Horario(2, "Miércoles", "Vigencia: 19 Nov 2026 - 30 Nov 2026", "08:00 AM - 05:00 PM", "Máx. 5 citas/día"))

        actualizarEstadoVacio()
    }

    private fun actualizarEstadoVacio() {
        if (listaHorarios.isEmpty()) {
            emptyStateLayout.visibility = View.VISIBLE
            rvHorarios.visibility = View.GONE
        } else {
            emptyStateLayout.visibility = View.GONE
            rvHorarios.visibility = View.VISIBLE
        }
        adapter.notifyDataSetChanged()
    }
}
