package com.example.prevenapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
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
import com.example.prevenapp.ui.components.AppInputField
import com.example.prevenapp.ui.components.PrevenPrimaryBlue

@Composable
fun PasswordRecoveryEmailScreen(
    onNextClick: (String) -> Unit,
    onNeedHelpClick: () -> Unit
) {
    var correo by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F7))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "Recupera tu contraseña",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    AppInputField(
                        label = "",
                        value = correo,
                        onValueChange = { correo = it },
                        keyboardType = KeyboardType.Email,
                        placeholder = "Ingrese correo de recuperacion"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¿Necesitas ayuda?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrevenPrimaryBlue,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .clickable { onNeedHelpClick() }
                    )
                }

                // Next Arrow Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(PrevenPrimaryBlue, shape = RoundedCornerShape(8.dp))
                            .clickable { onNextClick(correo) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Siguiente",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PasswordRecoveryEmailScreenPreview() {
    PasswordRecoveryEmailScreen(
        onNextClick = {},
        onNeedHelpClick = {}
    )
}
