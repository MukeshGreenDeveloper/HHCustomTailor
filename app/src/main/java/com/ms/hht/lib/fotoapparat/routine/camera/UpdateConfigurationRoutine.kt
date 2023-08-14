package com.ms.hht.lib.fotoapparat.routine.camera

import com.ms.hht.lib.fotoapparat.configuration.Configuration
import com.ms.hht.lib.fotoapparat.hardware.CameraDevice
import com.ms.hht.lib.fotoapparat.hardware.Device
import kotlinx.coroutines.runBlocking

/**
 * Updates [Device] configuration.
 */
internal fun Device.updateDeviceConfiguration(newConfiguration: Configuration) {
    val cameraDevice = getSelectedCamera()

    updateConfiguration(newConfiguration)

    updateCameraConfiguration(cameraDevice = cameraDevice)
}

/**
 * Updates [CameraDevice] parameters.
 */
internal fun Device.updateCameraConfiguration(
        cameraDevice: CameraDevice
) = runBlocking {
    val cameraParameters = getCameraParameters(cameraDevice)
    val frameProcessor = getFrameProcessor()

    cameraDevice.updateParameters(
            cameraParameters = cameraParameters
    )

    cameraDevice.updateFrameProcessor(
            frameProcessor = frameProcessor
    )
}
