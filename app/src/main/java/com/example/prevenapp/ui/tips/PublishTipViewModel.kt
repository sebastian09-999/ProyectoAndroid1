package com.example.prevenapp.ui.tips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prevenapp.data.model.AttachedFile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublishTipViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishTipUiState())
    val uiState: StateFlow<PublishTipUiState> = _uiState.asStateFlow()

    companion object {
        const val MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024L // 5MB limit
    }

    fun onTitleChanged(newTitle: String) {
        _uiState.update {
            it.copy(
                title = newTitle,
                titleError = if (newTitle.isNotBlank()) null else it.titleError
            )
        }
    }

    fun onCategoryChanged(newCategory: String) {
        _uiState.update {
            it.copy(
                category = newCategory,
                categoryError = if (newCategory.isNotBlank()) null else it.categoryError
            )
        }
    }

    fun onDescriptionChanged(newDescription: String) {
        _uiState.update {
            it.copy(
                description = newDescription,
                descriptionError = if (newDescription.isNotBlank()) null else it.descriptionError
            )
        }
    }

    fun onSourceChanged(newSource: String) {
        _uiState.update { it.copy(source = newSource) }
    }

    fun onDateChanged(newDate: String) {
        _uiState.update { it.copy(date = newDate) }
    }

    fun onFileSelected(fileName: String, fileSize: Long, mimeType: String, uriString: String? = null) {
        if (fileSize > MAX_FILE_SIZE_BYTES) {
            _uiState.update {
                it.copy(
                    fileError = "El archivo excede el tamaño máximo permitido de 5MB."
                )
            }
            return
        }

        val isValidType = mimeType.startsWith("image/") || mimeType.contains("pdf", ignoreCase = true)
        if (!isValidType) {
            _uiState.update {
                it.copy(
                    fileError = "Solo se permiten imágenes (PNG, JPG, WEBP) o documentos PDF."
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                attachedFile = AttachedFile(
                    name = fileName,
                    sizeInBytes = fileSize,
                    mimeType = mimeType,
                    uriString = uriString
                ),
                fileError = null
            )
        }
    }

    fun onRemoveFile() {
        _uiState.update {
            it.copy(
                attachedFile = null,
                fileError = null
            )
        }
    }

    fun publishTip() {
        val currentState = _uiState.value

        // Validación de campos obligatorios
        val titleErr = if (currentState.title.isBlank()) "El título es obligatorio *" else null
        val categoryErr = if (currentState.category.isBlank()) "Debes seleccionar una categoría *" else null
        val descriptionErr = if (currentState.description.isBlank()) "La descripción o contenido es obligatorio *" else null

        // Validación de archivo adjunto si existe
        val fileErr = if (currentState.attachedFile != null && currentState.attachedFile.sizeInBytes > MAX_FILE_SIZE_BYTES) {
            "El archivo adjunto supera los 5MB."
        } else null

        if (titleErr != null || categoryErr != null || descriptionErr != null || fileErr != null) {
            _uiState.update {
                it.copy(
                    titleError = titleErr,
                    categoryError = categoryErr,
                    descriptionError = descriptionErr,
                    fileError = fileErr,
                    errorMessage = "Por favor, completa correctamente los campos obligatorios."
                )
            }
            return
        }

        // Si todas las validaciones son exitosas, iniciamos el envío reactivo
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            // Simulación de respuesta de API/BD
            delay(1500)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = true,
                    successMessage = "¡Tip de salud publicado con éxito en la comunidad!",
                    // Limpieza del formulario
                    title = "",
                    category = "",
                    description = "",
                    source = "",
                    date = "",
                    attachedFile = null,
                    titleError = null,
                    categoryError = null,
                    descriptionError = null,
                    fileError = null
                )
            }
        }
    }

    fun clearFeedbackMessages() {
        _uiState.update {
            it.copy(
                successMessage = null,
                errorMessage = null,
                isSuccess = false
            )
        }
    }
}
