package com.ms.hht.lib.fotoapparat.log

/**
 * Logger which does nothing.
 */
internal class DummyLogger : Logger {

    override fun log(message: String) {
        // Do nothing
    }

}