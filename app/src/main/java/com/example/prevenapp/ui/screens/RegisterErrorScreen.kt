package com.example.prevenapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prevenapp.ui.components.PrimaryButton
import com.example.prevenapp.ui.components.StatusCardView

@Composable
fun RegisterErrorScreen(
    errorMessage: String,
    onBackToRegisterClick: () -> Unit
) {
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
                .clickable { onBackToRegisterClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                StatusCardView(
                    isSuccess = false,
                    message = if (errorMessage.isNotBlank()) errorMessage else "Para crear la cuenta debes llenar el campo RH"
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = "Volver al formulario",
                    onClick = onBackToRegisterClick,
                    modifier = Modifier.width(220.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterErrorScreenPreview() {
    RegisterErrorScreen(
        errorMessage = "Para crear la cuenta debes llenar el campo RH",
        onBackToRegisterClick = {}
    )
}
