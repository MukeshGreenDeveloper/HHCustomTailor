package com.ms.hht.lib.fotoapparat.routine.camera

import com.ms.hht.lib.fotoapparat.characteristic.LensPosition
import com.ms.hht.lib.fotoapparat.configuration.CameraConfiguration
import com.ms.hht.lib.fotoapparat.error.CameraErrorCallback
import com.ms.hht.lib.fotoapparat.exception.camera.CameraException
import com.ms.hht.lib.fotoapparat.hardware.CameraDevice
import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.hardware.orientation.OrientationSensor
import com.ms.hht.lib.fotoapparat.selector.LensPositionSelector

/**
 * Switches to a new [LensPosition] camera. Will do nothing if [LensPosition] is same.
 *
 * Will restart preview automatically if existing camera has started its preview.
 */
internal fun Device.switchCamera(
        newLensPositionSelector: LensPositionSelector,
        newConfiguration: CameraConfiguration,
        mainThreadErrorCallback: CameraErrorCallback,
        orientationSensor: OrientationSensor
) {
    val oldCameraDevice = try {
        getSelectedCamera()
    } catch (e: IllegalStateException) {
        null
    }

    if (oldCameraDevice == null) {
        updateLensPositionSelector(newLensPositionSelector)
        updateConfiguration(newConfiguration)
    } else if (getLensPositionSelector() != newLensPositionSelector) {
        updateLensPositionSelector(newLensPositionSelector)
        updateConfiguration(newConfiguration)

        restartPreview(
                oldCameraDevice,
                orientationSensor,
                mainThreadErrorCallback
        )
    }
}

/**
 * Restarts the preview of 2 different [CameraDevice].
 */
internal fun Device.restartPreview(
        oldCameraDevice: CameraDevice,
        orientationSensor: OrientationSensor,
        mainThreadErrorCallback: CameraErrorCallback
) {
    stop(oldCameraDevice)

    try {
        start(orientationSensor)
    } catch (e: CameraException) {
        mainThreadErrorCallback(e)
    }
}



