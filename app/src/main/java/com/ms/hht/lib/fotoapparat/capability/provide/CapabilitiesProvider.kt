@file:Suppress("DEPRECATION")

package com.ms.hht.lib.fotoapparat.capability.provide

import android.hardware.Camera
import com.ms.hht.lib.fotoapparat.capability.Capabilities
import com.ms.hht.lib.fotoapparat.parameter.SupportedParameters
import com.ms.hht.lib.fotoapparat.parameter.camera.convert.*

/**
 * Returns the [com.ms.hht.lib.fotoapparat.capability.Capabilities] of the given [Camera].
 */
internal fun Camera.getCapabilities() = SupportedParameters(parameters).getCapabilities()

private fun SupportedParameters.getCapabilities(): Capabilities {
    return Capabilities(
            zoom = supportedZoom,
            flashModes = flashModes.extract { it.toFlash() },
            focusModes = focusModes.extract { it.toFocusMode() },
            maxFocusAreas = maxNumFocusAreas,
            canSmoothZoom = supportedSmoothZoom,
            maxMeteringAreas = maxNumMeteringAreas,
            jpegQualityRange = jpegQualityRange,
            exposureCompensationRange = exposureCompensationRange,
            antiBandingModes = supportedAutoBandingModes.extract(String::toAntiBandingMode),
            sensorSensitivities = sensorSensitivities.toSet(),
            previewFpsRanges = supportedPreviewFpsRanges.extract { it.toFpsRange() },
            pictureResolutions = pictureResolutions.mapSizes(),
            previewResolutions = previewResolutions.mapSizes()
    )
}

private fun <Parameter : Any, Code> List<Code>.extract(converter: (Code) -> Parameter?) = mapNotNull { converter(it) }.toSet()

private fun Collection<Camera.Size>.mapSizes() = map { it.toResolution() }.toSet()