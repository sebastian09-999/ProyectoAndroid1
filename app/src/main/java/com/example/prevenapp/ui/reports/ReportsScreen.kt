package com.example.prevenapp.ui.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prevenapp.data.model.Patient
import com.example.prevenapp.data.model.RiskLevel
import com.example.prevenapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel,
    onOpenDrawer: () -> Unit = {},
    onNavigateToNewReport: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearUserMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PrevenApp Professional", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú de navegación")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BackgroundLight
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // 1. Cabecera con datos del profesional y título principal
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ProfessionalHeaderCard()
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Reportes y Seguimiento",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Gestiona los expedientes y evalúa el nivel de riesgo de tus pacientes",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            viewModel.onNewReportClicked()
                            onNavigateToNewReport()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("+ Nuevo Reporte", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }

            // Campo de Búsqueda en tiempo real
            item {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::onSearchQueryChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White),
                    placeholder = { Text("Buscar paciente por nombre...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary)
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpiar búsqueda", tint = TextSecondary)
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = BorderSoft,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                )
            }

            // 2. Tarjetas de Métricas (KPIs)
            item {
                MetricsKpiSection(
                    assignedPatients = uiState.assignedPatientsCount,
                    highRiskCount = uiState.highRiskCount,
                    pendingReportsCount = uiState.pendingReportsCount
                )
            }

            // Seccional Titular Lista
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pacientes (${uiState.filteredPatients.size})",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            // 3. Lista de pacientes con estado de riesgo
            if (uiState.filteredPatients.isEmpty()) {
                item {
                    EmptyStateCard()
                }
            } else {
                items(
                    items = uiState.filteredPatients,
                    key = { it.id }
                ) { patient ->
                    PatientCardItem(
                        patient = patient,
                        onCreateReport = {
                            viewModel.onCreateReport(patient)
                            onNavigateToNewReport()
                        },
                        onViewHistory = { viewModel.onViewHistory(patient) },
                        onScheduleAppointment = { viewModel.onScheduleAppointment(patient) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfessionalHeaderCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSoft)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PurpleLightContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.MedicalServices,
                    contentDescription = null,
                    tint = PurplePrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Dra. Sofía Martínez",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Médico Especialista en Salud Preventiva",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }

            Surface(
                color = PurpleLightContainer,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Panel Activo",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PurplePrimary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun MetricsKpiSection(
    assignedPatients: Int,
    highRiskCount: Int,
    pendingReportsCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        KpiCard(
            title = "Pacientes Asignados",
            value = assignedPatients.toString(),
            icon = Icons.Outlined.People,
            iconBgColor = PurpleLightContainer,
            iconTintColor = PurplePrimary,
            modifier = Modifier.weight(1f)
        )
        KpiCard(
            title = "En Riesgo Alto",
            value = highRiskCount.toString(),
            icon = Icons.Outlined.Warning,
            iconBgColor = HighRiskBg,
            iconTintColor = HighRiskRed,
            modifier = Modifier.weight(1f)
        )
        KpiCard(
            title = "Reportes Pendientes",
            value = pendingReportsCount.toString(),
            icon = Icons.Outlined.Assignment,
            iconBgColor = MediumRiskBg,
            iconTintColor = MediumRiskOrange,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun KpiCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTintColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSoft)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTintColor, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = title,
                fontSize = 11.sp,
                color = TextSecondary,
                lineHeight = 13.sp
            )
        }
    }
}

@Composable
private fun PatientCardItem(
    patient: Patient,
    onCreateReport: () -> Unit,
    onViewHistory: () -> Unit,
    onScheduleAppointment: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSoft)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(PurpleLightContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = patient.name.take(2).uppercase(),
                            fontWeight = FontWeight.Bold,
                            color = PurplePrimary,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = patient.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "${patient.age} años • Úl. reporte: ${patient.lastReportDate}",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                RiskBadge(riskLevel = patient.riskLevel)
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = BorderSoft, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Botones de acción por paciente
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onCreateReport,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(Icons.Default.PostAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reporte", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = onViewHistory,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSoft),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp), tint = TextPrimary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Historial", fontSize = 12.sp, color = TextPrimary)
                }

                IconButton(
                    onClick = onScheduleAppointment,
                    modifier = Modifier
                        .border(1.dp, BorderSoft, RoundedCornerShape(10.dp))
                        .size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Event,
                        contentDescription = "Agendar Cita",
                        tint = PurplePrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun RiskBadge(riskLevel: RiskLevel) {
    Surface(
        color = riskLevel.containerColor,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, riskLevel.borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(riskLevel.contentColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = riskLevel.label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = riskLevel.contentColor
            )
        }
    }
}

@Composable
private fun EmptyStateCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSoft)
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Outlined.SearchOff,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = TextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "No se encontraron pacientes",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Prueba ingresando otro término de búsqueda en la barra superior.",
                fontSize = 13.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
