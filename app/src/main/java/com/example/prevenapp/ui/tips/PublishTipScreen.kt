package com.example.prevenapp.ui.tips

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prevenapp.data.model.TipCategories
import com.example.prevenapp.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishTipScreen(
    viewModel: PublishTipViewModel,
    onBackToTips: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    // Selector de archivos del dispositivo
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            val (fileName, fileSize, mimeType) = getFileInfo(context, selectedUri)
            viewModel.onFileSelected(fileName, fileSize, mimeType, selectedUri.toString())
        }
    }

    // Selector de fecha
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                            val formattedDate = formatter.format(Date(millis))
                            viewModel.onDateChanged(formattedDate)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar", color = PurplePrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    LaunchedEffect(uiState.successMessage, uiState.errorMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearFeedbackMessages()
        }
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearFeedbackMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PrevenApp Professional", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBackToTips) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a mis Tips",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BackgroundLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Encabezado y retorno
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBackToTips() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = PurplePrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Volver a mis Tips",
                    color = PurplePrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column {
                Text(
                    text = "Publicar un Nuevo Tip",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Comparte información verificada y consejos de salud con la comunidad",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }

            // 2. Tarjeta contenedora del formulario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSoft)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Campo 1: Título del Tip (Obligatorio)
                    Column {
                        Row {
                            Text("Título del Tip", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                            Text(" *", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = HighRiskRed)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = uiState.title,
                            onValueChange = viewModel::onTitleChanged,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Ej: La importancia de los métodos anticonceptivos", fontSize = 13.sp) },
                            isError = uiState.titleError != null,
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurplePrimary,
                                unfocusedBorderColor = BorderSoft
                            )
                        )
                        uiState.titleError?.let {
                            Text(it, color = HighRiskRed, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }

                    // Campo 2: Categoría (Obligatorio Dropdown)
                    Column {
                        Row {
                            Text("Categoría", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                            Text(" *", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = HighRiskRed)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        CategoryDropdownMenu(
                            selectedCategory = uiState.category,
                            onCategorySelected = viewModel::onCategoryChanged,
                            isError = uiState.categoryError != null
                        )
                        uiState.categoryError?.let {
                            Text(it, color = HighRiskRed, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }

                    // Campo 3: Descripción / Contenido (Obligatorio Multilínea)
                    Column {
                        Row {
                            Text("Descripción / Contenido", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                            Text(" *", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = HighRiskRed)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = uiState.description,
                            onValueChange = viewModel::onDescriptionChanged,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            placeholder = { Text("Escribe aquí la información detallada, recomendaciones o consejos de salud...", fontSize = 13.sp) },
                            isError = uiState.descriptionError != null,
                            minLines = 4,
                            maxLines = 8,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurplePrimary,
                                unfocusedBorderColor = BorderSoft
                            )
                        )
                        uiState.descriptionError?.let {
                            Text(it, color = HighRiskRed, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }

                    // Campo 4: Fuente de información (Opcional)
                    Column {
                        Text("Fuente de información (Opcional)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = uiState.source,
                            onValueChange = viewModel::onSourceChanged,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Ej: Organización Mundial de la Salud (OMS)", fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurplePrimary,
                                unfocusedBorderColor = BorderSoft
                            )
                        )
                    }

                    // Campo 5: Fecha (Opcional mm/dd/yyyy)
                    Column {
                        Text("Fecha (Opcional)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = uiState.date,
                            onValueChange = viewModel::onDateChanged,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("mm/dd/yyyy", fontSize = 13.sp) },
                            trailingIcon = {
                                IconButton(onClick = { showDatePicker = true }) {
                                    Icon(Icons.Outlined.DateRange, contentDescription = "Seleccionar fecha", tint = PurplePrimary)
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurplePrimary,
                                unfocusedBorderColor = BorderSoft
                            )
                        )
                    }

                    // Campo 6: Adjuntar archivo (Opcional, Máx 5MB)
                    Column {
                        Text("Adjuntar archivo (Opcional)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                        Text("Permite imágenes (PNG, JPG) o PDF. Límite máximo de 5MB.", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(8.dp))

                        if (uiState.attachedFile == null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, if (uiState.fileError != null) HighRiskRed else BorderSoft, RoundedCornerShape(12.dp))
                                    .background(PurpleLightContainer.copy(alpha = 0.5f))
                                    .clickable { filePickerLauncher.launch("*/*") }
                                    .padding(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Outlined.CloudUpload,
                                        contentDescription = null,
                                        tint = PurplePrimary,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "Seleccionar archivo desde el dispositivo",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp,
                                        color = PurplePrimary
                                    )
                                    Text(
                                        "Imágenes o PDF • Máx. 5MB",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        } else {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = PurpleLightContainer)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(14.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Outlined.InsertDriveFile,
                                        contentDescription = null,
                                        tint = PurplePrimary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = uiState.attachedFile!!.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = TextPrimary,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = uiState.attachedFile!!.formattedSize,
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                    }
                                    IconButton(onClick = viewModel::onRemoveFile) {
                                        Icon(
                                            Icons.Outlined.Delete,
                                            contentDescription = "Eliminar archivo adjunto",
                                            tint = HighRiskRed
                                        )
                                    }
                                }
                            }
                        }

                        uiState.fileError?.let {
                            Text(it, color = HighRiskRed, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón Principal de Envío
                    Button(
                        onClick = viewModel::publishTip,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !uiState.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Publicar Tip", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryDropdownMenu(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = if (selectedCategory.isBlank()) "Selecciona una categoría" else selectedCategory,
            onValueChange = {},
            readOnly = true,
            isError = isError,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PurplePrimary,
                unfocusedBorderColor = BorderSoft
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            TipCategories.list.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category, fontSize = 14.sp) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

// Función auxiliar para obtener nombre, tamaño y MIME type de un Uri seleccionado
private fun getFileInfo(context: Context, uri: Uri): Triple<String, Long, String> {
    var name = "archivo_adjunto"
    var size = 0L
    val mimeType = context.contentResolver.getType(uri) ?: "application/octet-stream"

    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
        if (cursor.moveToFirst()) {
            if (nameIndex != -1) name = cursor.getString(nameIndex) ?: name
            if (sizeIndex != -1) size = cursor.getLong(sizeIndex)
        }
    }
    return Triple(name, size, mimeType)
}
