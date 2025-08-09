package com.mertswork.footyreserve.core.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.*
import coil3.compose.AsyncImage


@Composable
actual fun ProfileImagePicker(
    imageUri: String?,
    onImageSelected: (String?) -> Unit
) {
    var showImagePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { showImagePicker = true },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Photo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Add Photo",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showImagePicker) {
        LaunchedEffect(showImagePicker) {
            presentImagePicker { selectedUri ->
                onImageSelected(selectedUri)
                showImagePicker = false
            }
        }
    }
}

private suspend fun presentImagePicker(onImageSelected: (String?) -> Unit) {
    // Create PHPickerConfiguration
    val configuration = PHPickerConfiguration()
    configuration.selectionLimit = 1
    configuration.filter = PHPickerFilter.imagesFilter

    // Create picker controller
    val picker = PHPickerViewController(configuration)

    // Set up delegate - this is a simplified version
    // In a real implementation, you'd need to create a proper delegate
    // that handles the picker results and converts to URI

    // For now, return null as placeholder
    // You'll need to implement the actual picker presentation logic
    // using UIKit interop and delegates
    onImageSelected(null)
}