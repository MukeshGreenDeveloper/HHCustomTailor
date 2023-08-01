package com.ms.hht.lib.fotoapparat.routine.focus

import com.ms.hht.lib.fotoapparat.hardware.Device
import com.ms.hht.lib.fotoapparat.result.FocusResult
import kotlinx.coroutines.runBlocking

/**
 * Focuses the camera.
 */
internal fun Device.focus(): FocusResult = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.autoFocus()
}
