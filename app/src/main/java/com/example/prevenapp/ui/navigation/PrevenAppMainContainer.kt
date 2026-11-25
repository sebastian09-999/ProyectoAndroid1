package com.example.prevenapp.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prevenapp.ui.reports.ReportsScreen
import com.example.prevenapp.ui.reports.ReportsViewModel
import com.example.prevenapp.ui.tips.PublishTipScreen
import com.example.prevenapp.ui.tips.PublishTipViewModel
import com.example.prevenapp.ui.theme.BackgroundLight
import com.example.prevenapp.ui.theme.PurplePrimary
import com.example.prevenapp.ui.theme.TextPrimary
import com.example.prevenapp.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun PrevenAppMainContainer() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavRoutes.ReportesSeguimiento.route

    // Instanciación de ViewModels
    val reportsViewModel: ReportsViewModel = viewModel()
    val publishTipViewModel: PublishTipViewModel = viewModel()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route.route) {
                        launchSingleTop = true
                        popUpTo(NavRoutes.ReportesSeguimiento.route) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onCloseDrawer = {
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.ReportesSeguimiento.route
        ) {
            // APARTADO 1: Reportes y Seguimiento
            composable(NavRoutes.ReportesSeguimiento.route) {
                ReportsScreen(
                    viewModel = reportsViewModel,
                    onOpenDrawer = {
                        coroutineScope.launch { drawerState.open() }
                    },
                    onNavigateToNewReport = {
                        navController.navigate(NavRoutes.PublicarTips.route)
                    }
                )
            }

            // APARTADO 2: Publicar un Nuevo Tip
            composable(NavRoutes.PublicarTips.route) {
                PublishTipScreen(
                    viewModel = publishTipViewModel,
                    onBackToTips = {
                        navController.popBackStack()
                    }
                )
            }

            // Secciones complementarias del panel profesional
            composable(NavRoutes.Resumen.route) {
                GenericSectionScreen("Resumen del Profesional", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
            }
            composable(NavRoutes.MisHorarios.route) {
                GenericSectionScreen("Mis Horarios y Citas", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
            }
            composable(NavRoutes.SalasChat.route) {
                GenericSectionScreen("Salas de Chat con Pacientes", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
            }
            composable(NavRoutes.RecursosEducativos.route) {
                GenericSectionScreen("Recursos Educativos", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
            }
            composable(NavRoutes.MiPerfil.route) {
                GenericSectionScreen("Mi Perfil Profesional", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
            }
            composable(NavRoutes.CerrarSesion.route) {
                GenericSectionScreen("Sesión Cerrada", onOpenDrawer = { coroutineScope.launch { drawerState.open() } })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenericSectionScreen(
    title: String,
    onOpenDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = BackgroundLight
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sección disponible en el módulo profesional de PrevenApp.",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
