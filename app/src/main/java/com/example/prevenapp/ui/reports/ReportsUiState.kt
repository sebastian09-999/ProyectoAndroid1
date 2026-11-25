package com.example.prevenapp.ui.reports

import com.example.prevenapp.data.model.Patient

data class ReportsUiState(
    val searchQuery: String = "",
    val patients: List<Patient> = emptyList(),
    val filteredPatients: List<Patient> = emptyList(),
    val assignedPatientsCount: Int = 0,
    val highRiskCount: Int = 0,
    val pendingReportsCount: Int = 0,
    val isLoading: Boolean = false,
    val userMessage: String? = null
)
