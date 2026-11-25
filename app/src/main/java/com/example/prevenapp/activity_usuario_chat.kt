package com.example.prevenapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

data class MensajeChat(
    val id: String = UUID.randomUUID().toString(),
    val texto: String,
    val esMio: Boolean
)

class activity_usuario_chat : AppCompatActivity() {

    private lateinit var adapter: MensajesAdapter
    private val listaMensajes = mutableListOf<MensajeChat>()
    private var canalActual = "PRIVADO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_chat)

        val btnVolver = findViewById<Button>(R.id.btnVolverChat)
        val tvTituloCanal = findViewById<TextView>(R.id.tvTituloCanal)
        val tvEstadoConfidencial = findViewById<TextView>(R.id.tvEstadoConfidencial)
        val tvBloqueoAnonimo = findViewById<TextView>(R.id.tvBloqueoAnonimoChat)
        val btnPrivado = findViewById<Button>(R.id.btnCanalPrivado)
        val btnSalas = findViewById<Button>(R.id.btnCanalSalas)
        val rvMensajes = findViewById<RecyclerView>(R.id.rvMensajes)
        val etMensaje = findViewById<EditText>(R.id.etMensaje)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)

        btnVolver.setOnClickListener {
            finish()
        }

        adapter = MensajesAdapter(listaMensajes)
        rvMensajes.layoutManager = LinearLayoutManager(this)
        rvMensajes.adapter = adapter

        val esAnonimo = intent.getBooleanExtra("ES_ANONIMO", false)
        if (esAnonimo) {
            tvBloqueoAnonimo.visibility = View.VISIBLE
            etMensaje.isEnabled = false
            btnEnviar.isEnabled = false
        }

        cargarCanal("PRIVADO", tvTituloCanal, tvEstadoConfidencial)

        btnPrivado.setOnClickListener {
            cargarCanal("PRIVADO", tvTituloCanal, tvEstadoConfidencial)
        }

        btnSalas.setOnClickListener {
            cargarCanal("SALAS", tvTituloCanal, tvEstadoConfidencial)
        }

        btnEnviar.setOnClickListener {
            if (esAnonimo) {
                Toast.makeText(this, "Debes iniciar sesión para enviar mensajes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val texto = etMensaje.text.toString().trim()
            if (texto.isEmpty()) return@setOnClickListener

            adapter.agregarMensaje(MensajeChat(texto = texto, esMio = true))
            rvMensajes.scrollToPosition(listaMensajes.size - 1)
            etMensaje.text.clear()

            val respuesta = if (canalActual == "PRIVADO") {
                "Acompañante asignado: He recibido tu mensaje. Estoy aquí para orientarte de forma confidencial."
            } else {
                "Profesional moderador: Gracias por participar en la sala."
            }

            adapter.agregarMensaje(MensajeChat(texto = respuesta, esMio = false))
            rvMensajes.scrollToPosition(listaMensajes.size - 1)
        }
    }

    private fun cargarCanal(canal: String, tvTitulo: TextView, tvSubtitulo: TextView) {
        canalActual = canal
        listaMensajes.clear()
        adapter.notifyDataSetChanged()

        val mensajeInicial = if (canal == "PRIVADO") {
            tvTitulo.text = "Chat Personal con Profesional"
            tvSubtitulo.text = "Espacio privado 1 a 1 con tu acompañante asignado"
            "Hola. Este es tu chat personal y confidencial. Puedes compartir tus dudas o inquietudes con tranquilidad."
        } else {
            tvTitulo.text = "Salas Grupales de Orientación"
            tvSubtitulo.text = "Sala activa: Prevención y Autocuidado (Moderada por profesional)"
            "Bienvenido a la sala grupal creada por el profesional de salud. Recuerda mantener el respeto y la confidencialidad."
        }

        adapter.agregarMensaje(MensajeChat(texto = mensajeInicial, esMio = false))
    }
}

class MensajesAdapter(private val mensajes: MutableList<MensajeChat>) :
    RecyclerView.Adapter<MensajesAdapter.MensajeViewHolder>() {

    class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contenedor: LinearLayout = view.findViewById(R.id.contenedorBurbuja)
        val tvMensaje: TextView = view.findViewById(R.id.tvTextoMensaje)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mensaje_chat, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val item = mensajes[position]
        holder.tvMensaje.text = item.texto

        if (item.esMio) {
            holder.contenedor.gravity = Gravity.END
            holder.tvMensaje.setBackgroundColor(Color.parseColor("#2563EB"))
            holder.tvMensaje.setTextColor(Color.WHITE)
        } else {
            holder.contenedor.gravity = Gravity.START
            holder.tvMensaje.setBackgroundColor(Color.parseColor("#E2E8F0"))
            holder.tvMensaje.setTextColor(Color.parseColor("#1E293B"))
        }
    }

    override fun getItemCount(): Int = mensajes.size

    fun agregarMensaje(nuevo: MensajeChat) {
        mensajes.add(nuevo)
        notifyItemInserted(mensajes.size - 1)
    }
}