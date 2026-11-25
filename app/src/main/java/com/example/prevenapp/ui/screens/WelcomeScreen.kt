package com.example.prevenapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prevenapp.ui.components.PrevenAppLogoHeader
import com.example.prevenapp.ui.components.PrimaryButton

@Composable
fun WelcomeScreen(
    onEmpezarClick: () -> Unit,
    onLoginProfesionalClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F7))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Inner phone-like container
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .background(Color.White, shape = RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Centered Logo & Slogan
                PrevenAppLogoHeader()

                // Buttons
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    PrimaryButton(
                        text = "Empezar ahora",
                        onClick = onEmpezarClick
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Acceso Login Profesional",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4C75FF),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { onLoginProfesionalClick() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onEmpezarClick = {},
        onLoginProfesionalClick = {}
    )
}
