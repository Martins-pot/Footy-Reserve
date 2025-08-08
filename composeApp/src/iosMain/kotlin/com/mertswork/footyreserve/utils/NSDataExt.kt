package com.mertswork.footyreserve.utils

import com.mertswork.footyreserve.home.data.dto.ImagePicker
import com.mertswork.footyreserve.home.data.dto.ImageData

class IOSImagePicker : ImagePicker {
    override suspend fun pickImage(): PickedImage? = suspendCoroutine { continuation ->
        val picker = UIImagePickerController().apply {
            sourceType = UIImagePickerControllerSourceTypePhotoLibrary
            allowsEditing = false
            delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>?
                ) {
                    val image = didFinishPickingMediaWithInfo?.get(UIImagePickerControllerOriginalImage) as? UIImage
                    val data = image?.pngData()
                    val bytes = data?.toByteArray() // from NSData extension
                    val name = "selected_image.png"
                    picker.dismissViewControllerAnimated(true, null)
                    if (bytes != null) continuation.resume(PickedImage(bytes, name))
                    else continuation.resume(null)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    picker.dismissViewControllerAnimated(true, null)
                    continuation.resume(null)
                }
            }
        }
        val controller = getTopViewController()
        controller.presentViewController(picker, true, null)
    }
}


fun NSData.toByteArray(): ByteArray {
    val bytes = ByteArray(this.length.toInt())
    memScoped {
        val buffer = bytes.toCValues().ptr
        memcpy(buffer, this@toByteArray.bytes, this@toByteArray.length)
    }
    return bytes
}
