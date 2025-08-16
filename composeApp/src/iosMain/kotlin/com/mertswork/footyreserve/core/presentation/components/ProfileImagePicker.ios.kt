package com.mertswork.footyreserve.core.presentation.components


import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.*
import coil3.compose.AsyncImage
import com.mertswork.footyreserve.ui.theme.Dimens
import footyreserve.composeapp.generated.resources.Res
import footyreserve.composeapp.generated.resources.click_to_upload_image
import footyreserve.composeapp.generated.resources.select_image
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun ProfileImagePicker(
    imageUri: String?,
    onImageSelected: (String?) -> Unit
) {
    var showImagePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
//            .clip(CircleShape)
//            .background(MaterialTheme.colorScheme.surfaceVariant)
            ,
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }  else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
//            .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { showImagePicker = true },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.select_image),
                        contentDescription = "Add Photo",
//                    tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(100.dp)
                    )
                }
                Spacer(
                    Modifier.height(14.dp)
                )
                Text(
                    text = stringResource(Res.string.click_to_upload_image),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontSize = Dimens.Title
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

@OptIn(ExperimentalForeignApi::class)
private suspend fun presentImagePicker(onImageSelected: (String?) -> Unit) {
    val configuration = PHPickerConfiguration()
    configuration.selectionLimit = 1
    configuration.filter = PHPickerFilter.imagesFilter

    val picker = PHPickerViewController(configuration)
    val delegate = IOSImagePickerDelegate(onImageSelected)
    picker.delegate = delegate

    // Get the root view controller and present
    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(picker, true, null)
}

@OptIn(ExperimentalForeignApi::class)
class IOSImagePickerDelegate(
    private val onImageSelected: (String?) -> Unit
) : NSObject(), PHPickerViewControllerDelegateProtocol {

    override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
        picker.dismissViewControllerAnimated(true, null)

        val results = didFinishPicking.filterIsInstance<PHPickerResult>()
        if (results.isEmpty()) {
            onImageSelected(null)
            return
        }

        val result = results.first()
        val itemProvider = result.itemProvider

        if (itemProvider.canLoadObjectOfClass(UIImage)) {
            itemProvider.loadObjectOfClass(UIImage) { image, error ->
                if (error == null && image is UIImage) {
                    // Save image to temporary file
                    val tempUrl = saveImageToTempFile(image)
                    onImageSelected(tempUrl)
                } else {
                    onImageSelected(null)
                }
            }
        } else {
            onImageSelected(null)
        }
    }

    private fun saveImageToTempFile(image: UIImage): String? {
        return try {
            val imageData = UIImageJPEGRepresentation(image, 0.8) ?: return null
            val tempDir = NSTemporaryDirectory()
            val fileName = "temp_image_${NSUUID().UUIDString}.jpg"
            val filePath = "$tempDir$fileName"
            val fileUrl = NSURL.fileURLWithPath(filePath)

            val success = imageData.writeToURL(fileUrl, true)
            if (success) filePath else null
        } catch (e: Exception) {
            println("Error saving temp image: ${e.message}")
            null
        }
    }
}