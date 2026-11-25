package com.example.prevenapp.data.model

import java.util.Locale

data class AttachedFile(
    val name: String,
    val sizeInBytes: Long,
    val mimeType: String,
    val uriString: String? = null
) {
    val formattedSize: String
        get() {
            val kb = sizeInBytes / 1024.0
            val mb = kb / 1024.0
            return if (mb >= 1.0) {
                String.format(Locale.US, "%.2f MB", mb)
            } else {
                String.format(Locale.US, "%.0f KB", kb)
            }
        }
}
