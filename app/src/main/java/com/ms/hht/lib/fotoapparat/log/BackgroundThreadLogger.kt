package com.ms.hht.lib.fotoapparat.log

import com.ms.hht.lib.fotoapparat.hardware.executeLoggingThread

/**
 * Wrapper around another [Logger] which moves execution to a dedicated logging thread.
 */
internal class BackgroundThreadLogger(private val logger: Logger) : Logger {

    override fun log(message: String) {
        executeLoggingThread { logger.log(message) }
    }

}