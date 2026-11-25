package com.example.prevenapp.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prevenapp.data.model.Patient
import com.example.prevenapp.data.model.RiskLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadPatientsData()
    }

    private fun loadPatientsData() {
        _uiState.update { it.copy(isLoading = true) }

        // Datos iniciales de prueba (simulando una API o base de datos)
        val initialPatients = listOf(
            Patient("1", "Ana María Gómez", 28, "14 de Febrero, 2025", RiskLevel.ALTO, pendingReports = 2, totalReports = 5),
            Patient("2", "Carlos Eduardo Pérez", 45, "10 de Febrero, 2025", RiskLevel.MEDIO, pendingReports = 1, totalReports = 3),
            Patient("3", "Lucía Fernández", 32, "08 de Febrero, 2025", RiskLevel.BAJO, pendingReports = 0, totalReports = 4),
            Patient("4", "Roberto Torres", 58, "01 de Febrero, 2025", RiskLevel.ALTO, pendingReports = 1, totalReports = 8),
            Patient("5", "Valeria Ramírez", 22, "25 de Enero, 2025", RiskLevel.BAJO, pendingReports = 0, totalReports = 2),
            Patient("6", "Javier Mendoza", 39, "18 de Enero, 2025", RiskLevel.MEDIO, pendingReports = 1, totalReports = 6)
        )

        updateStateWithPatients(initialPatients, _uiState.value.searchQuery)
    }

    fun onSearchQueryChanged(query: String) {
        updateStateWithPatients(_uiState.value.patients, query)
    }

    private fun updateStateWithPatients(allPatients: List<Patient>, query: String) {
        val filtered = if (query.isBlank()) {
            allPatients
        } else {
            allPatients.filter { patient ->
                patient.name.contains(query, ignoreCase = true)
            }
        }

        // Recálculo dinámico de KPIs basados en todos los pacientes asignados
        val totalAssigned = allPatients.size
        val highRisk = allPatients.count { it.riskLevel == RiskLevel.ALTO }
        val pendingReports = allPatients.sumOf { it.pendingReports }

        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                patients = allPatients,
                filteredPatients = filtered,
                assignedPatientsCount = totalAssigned,
                highRiskCount = highRisk,
                pendingReportsCount = pendingReports,
                isLoading = false
            )
        }
    }

    fun onCreateReport(patient: Patient) {
        _uiState.update {
            it.copy(userMessage = "Iniciando nuevo reporte para ${patient.name}")
        }
    }

    fun onViewHistory(patient: Patient) {
        _uiState.update {
            it.copy(userMessage = "Abriendo historial clínico de ${patient.name}")
        }
    }

    fun onScheduleAppointment(patient: Patient) {
        _uiState.update {
            it.copy(userMessage = "Agendando cita para ${patient.name}")
        }
    }

    fun onNewReportClicked() {
        _uiState.update {
            it.copy(userMessage = "Formulario de nuevo reporte abierto")
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }
}
