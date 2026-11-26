package com.example.prevenapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

class CitasActivity : AppCompatActivity() {

    private var fechaSeleccionada: String? = null
    private var horaSeleccionada: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        val btnMotivoPsico = findViewById<Button>(R.id.btnMotivoPsico)
        val btnMotivoPrev = findViewById<Button>(R.id.btnMotivoPrev)
        val btnMotivoEstres = findViewById<Button>(R.id.btnMotivoEstres)
        val etMotivoCita = findViewById<EditText>(R.id.etMotivoCita)

        val btnElegirFecha = findViewById<Button>(R.id.btnElegirFecha)
        val btnElegirHora = findViewById<Button>(R.id.btnElegirHora)
        val tvFechaSeleccionada = findViewById<TextView>(R.id.tvFechaSeleccionada)
        val tvHoraSeleccionada = findViewById<TextView>(R.id.tvHoraSeleccionada)

        val btnSepararCita = findViewById<Button>(R.id.btnSepararCita)
        val tvConfirmacionCita = findViewById<TextView>(R.id.tvConfirmacionCita)

        btnMotivoPsico.setOnClickListener {
            etMotivoCita.setText("Consulta de apoyo psicológico y orientación emocional")
        }

        btnMotivoPrev.setOnClickListener {
            etMotivoCita.setText("Control preventivo y hábitos saludables")
        }

        btnMotivoEstres.setOnClickListener {
            etMotivoCita.setText("Manejo del estrés, ansiedad y bienestar mental")
        }

        btnElegirFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                val fechaFormateada = String.format(Locale.getDefault(), "%02d/%02d/%04d", d, m + 1, y)
                fechaSeleccionada = fechaFormateada
                tvFechaSeleccionada.text = "Fecha: $fechaFormateada"
            }, anio, mes, dia).show()
        }

        btnElegirHora.setOnClickListener {
            val calendario = Calendar.getInstance()
            val horaActual = calendario.get(Calendar.HOUR_OF_DAY)
            val minutoActual = calendario.get(Calendar.MINUTE)

            // Permite elegir cualquier hora y minuto (00:00 a 23:59)
            TimePickerDialog(this, { _, hora, minuto ->
                val horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", hora, minuto)
                horaSeleccionada = horaFormateada
                tvHoraSeleccionada.text = "Hora: $horaFormateada (pendiente de sincronizar con agenda médica)"
            }, horaActual, minutoActual, true).show()
        }

        btnSepararCita.setOnClickListener {
            val motivo = etMotivoCita.text.toString().trim()
            val fecha = fechaSeleccionada
            val hora = horaSeleccionada

            when {
                motivo.isEmpty() -> {
                    tvConfirmacionCita.text = "Por favor selecciona o escribe el motivo de tu cita."
                }
                fecha == null -> {
                    tvConfirmacionCita.text = "Por favor elige una fecha para tu cita."
                }
                hora == null -> {
                    tvConfirmacionCita.text = "Por favor elige una hora para tu cita."
                }
                else -> {
                    val clinicaAsignada = obtenerClinicaSegunMotivo(motivo)
                    tvConfirmacionCita.text = """
                        Cita separada con éxito
                        • Motivo: $motivo
                        • Fecha: $fecha
                        • Hora: $hora (sincronización con agenda médica en curso)
                        • Clínica: $clinicaAsignada
                    """.trimIndent()
                }
            }
        }
    }

    private fun obtenerClinicaSegunMotivo(motivo: String): String {
        val texto = motivo.lowercase(Locale.getDefault())
        return when {
            texto.contains("psic") || texto.contains("estrés") || texto.contains("estres") ||
                    texto.contains("ansiedad") || texto.contains("emocional") || texto.contains("mental") ->
                "Clínica de Bienestar y Salud Mental — Sede Centro"
            texto.contains("prev") || texto.contains("hábito") || texto.contains("habito") ||
                    texto.contains("chequeo") ->
                "Unidad de Medicina Preventiva — Sede Norte"
            else ->
                "Clínica Integral PrevenApp — Consultorio General"
        }
    }
}