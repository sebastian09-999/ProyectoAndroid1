package com.example.prevenapp.ui.tips

import com.example.prevenapp.data.model.AttachedFile

data class PublishTipUiState(
    val title: String = "",
    val titleError: String? = null,
    val category: String = "",
    val categoryError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val source: String = "",
    val date: String = "",
    val attachedFile: AttachedFile? = null,
    val fileError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
