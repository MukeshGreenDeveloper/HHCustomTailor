package com.ms.hht.lib.fotoapparat.view

import com.ms.hht.lib.fotoapparat.parameter.Resolution
import com.ms.hht.lib.fotoapparat.parameter.ScaleType

/**
 * Renders the stream from [com.ms.hht.lib.fotoapparat.Fotoapparat].
 */
interface CameraRenderer {

    /**
     * Sets the scale type of the preview to the renderer. This method will be called from camera
     * thread, so it is safe to perform blocking operations here.
     */
    fun setScaleType(scaleType: ScaleType)

    /**
     * Sets the resolution at which the view should display the preview.
     */
    fun setPreviewResolution(resolution: Resolution)

    /**
     * Returns the surface texture when available.
     */
    fun getPreview(): Preview

}
