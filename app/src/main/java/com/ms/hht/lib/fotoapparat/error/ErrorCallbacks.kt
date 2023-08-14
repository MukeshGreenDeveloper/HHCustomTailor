package com.ms.hht.lib.fotoapparat.error

import android.os.Looper
import com.ms.hht.lib.fotoapparat.exception.camera.CameraException
import com.ms.hht.lib.fotoapparat.hardware.executeMainThread

typealias CameraErrorCallback = (CameraException) -> Unit

/**
 * @return CameraErrorCallback which will always move execution to the main thread.
 */
fun CameraErrorCallback.onMainThread(): CameraErrorCallback = { cameraException ->
    if (Looper.myLooper() == Looper.getMainLooper()) {
        this(cameraException)
    } else {
        executeMainThread { this(cameraException) }
    }
}
