package com.example.prevenapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatDetalleFragment : Fragment(R.layout.fragment_chat_detalle) {

    companion object {
        private const val ARG_TITULO = "titulo_sala"
        fun newInstance(titulo: String): ChatDetalleFragment {
            val fragment = ChatDetalleFragment()
            val args = Bundle()
            args.putString(ARG_TITULO, titulo)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: MensajeAdapter
    private val listaMensajes = mutableListOf<Mensaje>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitulo = view.findViewById<TextView>(R.id.tvTituloChat)
        val rvMensajes = view.findViewById<RecyclerView>(R.id.rvMensajes)
        val etMensaje = view.findViewById<EditText>(R.id.etMensaje)
        val btnEnviar = view.findViewById<ImageView>(R.id.btnEnviar)
        val btnVolver = view.findViewById<ImageView>(R.id.btnVolver)

        // Configurar título
        tvTitulo.text = arguments?.getString(ARG_TITULO) ?: "Chat"

        // Datos de prueba
        listaMensajes.add(Mensaje("Hola doctor, tengo una duda.", "Paciente", "10:00", false))
        listaMensajes.add(Mensaje("¡Hola! Claro, dime en qué te puedo ayudar.", "Tú", "10:02", true))

        adapter = MensajeAdapter(listaMensajes)
        rvMensajes.layoutManager = LinearLayoutManager(context)
        rvMensajes.adapter = adapter

        // Acción al enviar
        btnEnviar.setOnClickListener {
            val texto = etMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                listaMensajes.add(Mensaje(texto, "Tú", "Ahora", true))
                adapter.notifyItemInserted(listaMensajes.size - 1)
                etMensaje.text.clear()
                rvMensajes.scrollToPosition(listaMensajes.size - 1) // Bajar al último mensaje
            }
        }

        // Botón volver atrás
        btnVolver.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
