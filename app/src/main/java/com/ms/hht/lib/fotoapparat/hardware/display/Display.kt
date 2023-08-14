package com.ms.hht.lib.fotoapparat.hardware.display

import android.content.Context
import android.os.Build
import android.view.Surface
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.ms.hht.lib.fotoapparat.hardware.orientation.Orientation
import com.ms.hht.lib.fotoapparat.hardware.orientation.Orientation.Horizontal.Landscape
import com.ms.hht.lib.fotoapparat.hardware.orientation.Orientation.Horizontal.ReverseLandscape
import com.ms.hht.lib.fotoapparat.hardware.orientation.Orientation.Vertical.Portrait
import com.ms.hht.lib.fotoapparat.hardware.orientation.Orientation.Vertical.ReversePortrait

/**
 * A phone's display.
 */
internal open class Display(context: Context) {

    @RequiresApi(Build.VERSION_CODES.R)
    private val display = context.getDisplay()

    /**
     * Returns the orientation of the screen.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    open fun getOrientation(): Orientation = when (display!!.rotation) {
        Surface.ROTATION_0 -> Portrait
        Surface.ROTATION_90 -> Landscape
        Surface.ROTATION_180 -> ReversePortrait
        Surface.ROTATION_270 -> ReverseLandscape
        else -> Portrait
    }

}

private fun Context.getDisplay() = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
