@file:Suppress("DEPRECATION")

package com.ms.hht.lib.fotoapparat.parameter.camera.convert

import android.hardware.Camera
import com.ms.hht.lib.fotoapparat.parameter.Resolution

/**
 * Converts [Camera.Size] to [Resolution].
 */
fun Camera.Size.toResolution(): Resolution = Resolution(width, height)
