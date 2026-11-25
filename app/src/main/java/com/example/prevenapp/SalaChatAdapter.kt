package com.example.prevenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SalaChatAdapter(
    private val listaSalas: List<SalaChat>,
    private val onSalaClick: (SalaChat) -> Unit
) : RecyclerView.Adapter<SalaChatAdapter.SalaViewHolder>() {

    class SalaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTema: TextView = view.findViewById(R.id.tvTemaSala)
        val tvCreador: TextView = view.findViewById(R.id.tvCreadorSala)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sala_chat, parent, false)
        return SalaViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalaViewHolder, position: Int) {
        val sala = listaSalas[position]
        holder.tvTema.text = sala.tema
        holder.tvCreador.text = "Por: ${sala.creador}"

        holder.itemView.setOnClickListener { onSalaClick(sala) }
    }

    override fun getItemCount() = listaSalas.size
}