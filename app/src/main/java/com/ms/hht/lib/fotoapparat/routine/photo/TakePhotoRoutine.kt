package com.ms.hht.lib.fotoapparat.routine.photo

import com.ms.hht.lib.fotoapparat.exception.camera.CameraException
import com.ms.hht.lib.fotoapparat.hardware.CameraDevice
import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.result.Photo
import kotlinx.coroutines.runBlocking

/**
 * Takes a photo.
 */
internal fun Device.takePhoto(): Photo = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.takePhoto().also {
        cameraDevice.startPreviewSafely()
    }
}

private fun CameraDevice.startPreviewSafely() {
    try {
        startPreview()
    } catch (ignore: CameraException) {
    }
}
