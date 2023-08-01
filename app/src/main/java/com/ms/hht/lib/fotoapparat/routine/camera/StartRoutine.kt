package com.ms.hht.lib.fotoapparat.routine.camera

import com.ms.hht.lib.fotoapparat.concurrent.CameraExecutor.Operation
import com.ms.hht.lib.fotoapparat.error.CameraErrorCallback
import com.ms.hht.lib.fotoapparat.exception.camera.CameraException
import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.hardware.orientation.OrientationSensor
import com.ms.hht.lib.fotoapparat.routine.focus.focusOnPoint
import com.ms.hht.lib.fotoapparat.routine.orientation.startOrientationMonitoring
import java.io.IOException

/**
 * Starts the camera from idle.
 */
internal fun Device.bootStart(
        orientationSensor: OrientationSensor,
        mainThreadErrorCallback: CameraErrorCallback
) {
    if (hasSelectedCamera()) {
        throw IllegalStateException("Camera has already started!")
    }

    try {
        start(
                orientationSensor = orientationSensor
        )
        startOrientationMonitoring(
                orientationSensor = orientationSensor
        )
    } catch (e: CameraException) {
        mainThreadErrorCallback(e)
    }
}

/**
 * Starts the camera.
 */
internal fun Device.start(orientationSensor: OrientationSensor) {
    selectCamera()

    val cameraDevice = getSelectedCamera().apply {
        open()

        updateCameraConfiguration(
                cameraDevice = this
        )
        setDisplayOrientation(orientationSensor.lastKnownOrientationState)
    }

    val previewResolution = cameraDevice.getPreviewResolution()

    cameraRenderer.apply {
        setScaleType(
                scaleType = scaleType
        )

        setPreviewResolution(
                resolution = previewResolution
        )
    }

    focusPointSelector?.setFocalPointListener { focalRequest ->
        executor.execute(Operation(cancellable = true) {
            focusOnPoint(focalRequest)
        })
    }

    with(cameraDevice) {
        try {
            setDisplaySurface(
                    preview = cameraRenderer.getPreview()
            )

            startPreview()
        } catch (e: IOException) {
            logger.log("Can't start preview because of the exception: $e")
        }
    }
}