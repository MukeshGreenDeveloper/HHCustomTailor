@file:Suppress("DEPRECATION")

package com.ms.hht.lib.fotoapparat.characteristic

import android.hardware.Camera
import com.ms.hht.lib.fotoapparat.hardware.orientation.toOrientation

/**
 * Returns the [Characteristics] for the given `cameraId`.
 */
internal fun getCharacteristics(cameraId: Int): Characteristics {
    val info = Camera.CameraInfo()
    Camera.getCameraInfo(cameraId, info)
    val lensPosition = info.facing.toLensPosition()
    return Characteristics(
            cameraId = cameraId,
            lensPosition = lensPosition,
            cameraOrientation = info.orientation.toOrientation(),
            isMirrored = lensPosition == LensPosition.Front
    )
}
