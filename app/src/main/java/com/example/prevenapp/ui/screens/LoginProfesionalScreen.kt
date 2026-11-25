package com.example.prevenapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prevenapp.ui.components.*

@Composable
fun LoginProfesionalScreen(
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    onRecuperarContrasenaClick: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var numDocumento by remember { mutableStateOf("") }
    var credencial by remember { mutableStateOf("") }
    var archivoAdjunto by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F7))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .fillMaxHeight(0.96f)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Login Profesional",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                AppInputField(
                    label = "Correo electrónico:",
                    value = correo,
                    onValueChange = { correo = it },
                    keyboardType = KeyboardType.Email,
                    placeholder = "Correo electrónico"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "Contraseña:",
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    isPassword = true,
                    placeholder = "Contraseña"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "Número de documento:",
                    value = numDocumento,
                    onValueChange = { numDocumento = it },
                    keyboardType = KeyboardType.Number,
                    placeholder = "Número de documento"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "Credencial profesional:",
                    value = credencial,
                    onValueChange = { credencial = it },
                    placeholder = "Credencial profesional"
                )

                Spacer(modifier = Modifier.height(12.dp))

                FilePickerField(
                    label = "Adjuntar archivo:",
                    selectedFileName = archivoAdjunto,
                    onPickFile = {
                        archivoAdjunto = "documento_credencial.pdf"
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PrimaryButton(
                        text = "INICIAR SESIÓN",
                        onClick = onLoginClick,
                        modifier = Modifier.weight(1f)
                    )

                    SecondaryButton(
                        text = "Atrás",
                        onClick = onBackClick,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Recuperar contraseña",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrevenPrimaryBlue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onRecuperarContrasenaClick() }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginProfesionalScreenPreview() {
    LoginProfesionalScreen(
        onLoginClick = {},
        onBackClick = {},
        onRecuperarContrasenaClick = {}
    )
}
