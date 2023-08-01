package com.ms.hht.lib.fotoapparat.hardware.metering

import com.ms.hht.lib.fotoapparat.parameter.Resolution

/**
 * The request to focus camera at a particular point.
 */
data class FocalRequest(

        /**
         * The point where when user would like to focus.
         */
        val point: PointF,

        /**
         * Resolution of the preview
         */
        val previewResolution: Resolution
)

