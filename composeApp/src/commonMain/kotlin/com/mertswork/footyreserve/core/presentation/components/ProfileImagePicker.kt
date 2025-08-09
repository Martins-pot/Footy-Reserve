package com.mertswork.footyreserve.core.presentation.components

import androidx.compose.runtime.Composable

@Composable
expect fun ProfileImagePicker(
    imageUri: String?,
    onImageSelected: (String?) -> Unit
)
