package com.example.prevenapp.data.model

data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val lastReportDate: String,
    val riskLevel: RiskLevel,
    val pendingReports: Int = 0,
    val totalReports: Int = 1
)
