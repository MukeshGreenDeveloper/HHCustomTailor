package com.ms.hht.lib.fotoapparat.routine.orientation

import com.ms.hht.lib.fotoapparat.hardware.orientation.OrientationSensor

/**
 * Stops orientation monitoring routine.
 */
internal fun OrientationSensor.stopMonitoring() = stop()

