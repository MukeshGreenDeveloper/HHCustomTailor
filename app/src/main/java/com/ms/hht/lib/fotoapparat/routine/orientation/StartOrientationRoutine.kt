package com.ms.hht.lib.fotoapparat.routine.orientation

import com.ms.hht.lib.fotoapparat.concurrent.CameraExecutor.Operation
import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.hardware.orientation.OrientationSensor
import com.ms.hht.lib.fotoapparat.hardware.orientation.OrientationState

/**
 * Starts orientation monitoring routine.
 */
internal fun Device.startOrientationMonitoring(
        orientationSensor: OrientationSensor
) {
    orientationSensor.start { orientationState: OrientationState ->
        executor.execute(Operation(cancellable = true) {
            val cameraDevice = getSelectedCamera()
            cameraDevice.setDisplayOrientation(orientationState)
        })
    }
}
