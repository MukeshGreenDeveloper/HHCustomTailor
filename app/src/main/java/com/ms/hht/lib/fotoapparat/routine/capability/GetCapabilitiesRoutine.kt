package com.ms.hht.lib.fotoapparat.routine.capability

import com.ms.hht.lib.fotoapparat.capability.Capabilities
import com.ms.hht.lib.fotoapparat.hardware.Device
import kotlinx.coroutines.runBlocking

/**
 * Returns the camera [Capabilities].
 */
internal fun Device.getCapabilities(): Capabilities = runBlocking {
    val cameraDevice = awaitSelectedCamera()

    cameraDevice.getCapabilities()
}

