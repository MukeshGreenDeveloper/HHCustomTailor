package com.ms.hht.lib.fotoapparat.exception.camera

/**
 * A generic camera exception.
 */
open class CameraException(
        message: String,
        cause: Throwable? = null
) : RuntimeException(
        message,
        cause
)