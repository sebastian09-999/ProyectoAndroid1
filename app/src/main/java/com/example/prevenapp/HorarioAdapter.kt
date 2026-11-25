package com.example.prevenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HorarioAdapter(
    private var listaHorarios: List<Horario>,
    private val onEditClick: (Horario) -> Unit,
    private val onDeleteClick: (Horario) -> Unit
) : RecyclerView.Adapter<HorarioAdapter.HorarioViewHolder>() {

    class HorarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDia: TextView = view.findViewById(R.id.tvDia)
        val tvVigencia: TextView = view.findViewById(R.id.tvVigencia)
        val tvHoras: TextView = view.findViewById(R.id.tvHoras)
        val tvCitas: TextView = view.findViewById(R.id.tvCitas)
        val btnEditar: ImageView = view.findViewById(R.id.btnEditar)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horario, parent, false)
        return HorarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorarioViewHolder, position: Int) {
        val horario = listaHorarios[position]
        holder.tvDia.text = horario.dia
        holder.tvVigencia.text = horario.vigencia
        holder.tvHoras.text = horario.horas
        holder.tvCitas.text = horario.limiteCitas

        holder.btnEditar.setOnClickListener { onEditClick(horario) }
        holder.btnEliminar.setOnClickListener { onDeleteClick(horario) }
    }

    override fun getItemCount() = listaHorarios.size
}