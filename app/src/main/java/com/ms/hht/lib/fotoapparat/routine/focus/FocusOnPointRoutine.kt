package com.ms.hht.lib.fotoapparat.routine.focus

import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.hardware.metering.FocalRequest
import com.ms.hht.lib.fotoapparat.result.FocusResult
import kotlinx.coroutines.runBlocking

/**
 * Focuses the camera on a particular point.
 */
internal fun Device.focusOnPoint(focalRequest: FocalRequest): FocusResult = runBlocking {
    awaitSelectedCamera()
            .run {
                setFocalPoint(focalRequest)
                autoFocus()
            }
}
