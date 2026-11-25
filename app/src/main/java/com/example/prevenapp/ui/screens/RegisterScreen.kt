package com.example.prevenapp.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prevenapp.ui.components.AppInputField
import com.example.prevenapp.ui.components.PrimaryButton

@Composable
fun RegisterScreen(
    onConfirmSuccess: () -> Unit,
    onConfirmError: (String) -> Unit
) {
    var nombres by remember { mutableStateOf("María") }
    var apellidos by remember { mutableStateOf("Campo") }
    var correo by remember { mutableStateOf("maria@gmail.com") }
    var telefono by remember { mutableStateOf("11111") }
    var rh by remember { mutableStateOf("A+") }
    var contrasena by remember { mutableStateOf("12345A.1910") }

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
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Formulario de registro",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Los campos con  *  son obligatorios",
                    fontSize = 11.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                AppInputField(
                    label = "Nombres:",
                    value = nombres,
                    onValueChange = { nombres = it },
                    isRequired = true,
                    placeholder = "María"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "Apellidos:",
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    isRequired = true,
                    placeholder = "Campo"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "Correo:",
                    value = correo,
                    onValueChange = { correo = it },
                    isRequired = true,
                    keyboardType = KeyboardType.Email,
                    placeholder = "maria@gmail.com"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "Teléfono:",
                    value = telefono,
                    onValueChange = { telefono = it },
                    isRequired = false,
                    keyboardType = KeyboardType.Phone,
                    placeholder = "11111"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "RH:",
                    value = rh,
                    onValueChange = { rh = it },
                    isRequired = true,
                    placeholder = "A+"
                )

                Spacer(modifier = Modifier.height(12.dp))

                AppInputField(
                    label = "Contraseña:",
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    isRequired = true,
                    isPassword = true,
                    placeholder = "12345A.1910"
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Confirmar",
                    onClick = {
                        if (rh.isBlank()) {
                            onConfirmError("Para crear la cuenta debes llenar el campo RH")
                        } else if (nombres.isBlank() || apellidos.isBlank() || correo.isBlank() || contrasena.isBlank()) {
                            onConfirmError("Por favor completa los campos obligatorios")
                        } else {
                            onConfirmSuccess()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onConfirmSuccess = {},
        onConfirmError = {}
    )
}
