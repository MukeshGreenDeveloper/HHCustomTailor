package com.ms.hht.lib.fotoapparat.parameter.camera

import com.ms.hht.lib.fotoapparat.hardware.CameraDevice
import com.ms.hht.lib.fotoapparat.parameter.*
import com.ms.hht.lib.fotoapparat.util.lineSeparator
import com.ms.hht.lib.fotoapparat.util.wrap

/**
 * Parameters of [CameraDevice].
 */
data class CameraParameters(
        val flashMode: Flash,
        val focusMode: FocusMode,
        val jpegQuality: Int,
        val exposureCompensation: Int,
        val previewFpsRange: FpsRange,
        val antiBandingMode: AntiBandingMode,
        val sensorSensitivity: Int?,
        val pictureResolution: Resolution,
        val previewResolution: Resolution
) {
    override fun toString(): String {
        return "CameraParameters" + lineSeparator +
                "flashMode:" + flashMode.wrap() +
                "focusMode:" + focusMode.wrap() +
                "jpegQuality:" + jpegQuality.wrap() +
                "exposureCompensation:" + exposureCompensation.wrap() +
                "previewFpsRange:" + previewFpsRange.wrap() +
                "antiBandingMode:" + antiBandingMode.wrap() +
                "sensorSensitivity:" + sensorSensitivity.wrap() +
                "pictureResolution:" + pictureResolution.wrap() +
                "previewResolution:" + previewResolution.wrap()
    }
}
