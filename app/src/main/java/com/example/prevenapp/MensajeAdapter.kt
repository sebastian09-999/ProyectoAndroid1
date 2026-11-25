package com.example.prevenapp

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MensajeAdapter(private val listaMensajes: List<Mensaje>) : RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAutor: TextView = view.findViewById(R.id.tvAutorMensaje)
        val tvTexto: TextView = view.findViewById(R.id.tvTextoMensaje)
        val layoutPrincipal: LinearLayout = view as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = listaMensajes[position]

        holder.tvTexto.text = mensaje.texto
        holder.tvAutor.text = if (mensaje.esPropio) "Tú" else mensaje.autor

        if (mensaje.esPropio) {
            // Alinear a la derecha y pintar de azul
            holder.layoutPrincipal.gravity = Gravity.END
            holder.tvTexto.setBackgroundColor(Color.parseColor("#4F46E5"))
            holder.tvTexto.setTextColor(Color.WHITE)
        } else {
            // Alinear a la izquierda y pintar de gris
            holder.layoutPrincipal.gravity = Gravity.START
            holder.tvTexto.setBackgroundColor(Color.parseColor("#E2E8F0"))
            holder.tvTexto.setTextColor(Color.parseColor("#1F2937"))
        }
    }

    override fun getItemCount() = listaMensajes.size
}
