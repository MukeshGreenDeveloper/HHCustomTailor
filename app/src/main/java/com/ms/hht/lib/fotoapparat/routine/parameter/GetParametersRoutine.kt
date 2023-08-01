package com.ms.hht.lib.fotoapparat.routine.parameter

import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.parameter.camera.CameraParameters
import kotlinx.coroutines.runBlocking

/**
 * Returns the current [CameraParameters].
 */
internal fun Device.getCurrentParameters(): CameraParameters = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.getParameters()
}
