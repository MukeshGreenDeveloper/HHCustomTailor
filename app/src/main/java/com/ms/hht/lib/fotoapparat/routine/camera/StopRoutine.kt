package com.ms.hht.lib.fotoapparat.routine.camera

import com.ms.hht.lib.fotoapparat.hardware.CameraDevice
import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.hardware.orientation.OrientationSensor
import com.ms.hht.lib.fotoapparat.routine.orientation.stopMonitoring

/**
 * Stops the camera completely.
 */
internal fun Device.shutDown(
        orientationSensor: OrientationSensor
) {
    focusPointSelector?.setFocalPointListener { }
    orientationSensor.stopMonitoring()

    val cameraDevice = getSelectedCamera()

    stop(cameraDevice)
}

/**
 * Stops the camera.
 */
internal fun Device.stop(cameraDevice: CameraDevice) {
    cameraDevice.stopPreview()

    cameraDevice.close()

    clearSelectedCamera()
}
