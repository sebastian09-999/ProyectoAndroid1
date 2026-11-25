package com.example.prevenapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.prevenapp.ui.navigation.PrevenAppMainContainer
import com.example.prevenapp.ui.theme.PrevenAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrevenAppTheme {
                PrevenAppMainContainer()
            }
        }
    }
}
