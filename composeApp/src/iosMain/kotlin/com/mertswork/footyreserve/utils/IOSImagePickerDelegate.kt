package com.mertswork.footyreserve.utils


import kotlinx.cinterop.*
import platform.Foundation.*
import platform.PhotosUI.*
import platform.UIKit.*
import platform.darwin.NSObject

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
                    // Convert UIImage to file URL or data URL
                    // This is simplified - you'd need to save to temp file
                    // and return the file URL
                    onImageSelected("temp_image_url")
                } else {
                    onImageSelected(null)
                }
            }
        } else {
            onImageSelected(null)
        }
    }
}