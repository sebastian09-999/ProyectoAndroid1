package com.example.prevenapp.data.model

import androidx.compose.ui.graphics.Color

enum class RiskLevel(
    val label: String,
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color
) {
    ALTO(
        label = "Riesgo Alto",
        containerColor = Color(0xFFFFEBEE),
        contentColor = Color(0xFFC62828),
        borderColor = Color(0xFFFFCDD2)
    ),
    MEDIO(
        label = "Riesgo Medio",
        containerColor = Color(0xFFFFF3E0),
        contentColor = Color(0xFFE65100),
        borderColor = Color(0xFFFFE0B2)
    ),
    BAJO(
        label = "Riesgo Bajo",
        containerColor = Color(0xFFE8F5E9),
        contentColor = Color(0xFF2E7D32),
        borderColor = Color(0xFFC8E6C9)
    )
}
