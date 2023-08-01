package com.ms.hht.lib.fotoapparat.configuration

import com.ms.hht.lib.fotoapparat.selector.*
import com.ms.hht.lib.fotoapparat.util.FrameProcessor

interface Configuration {
    val flashMode: FlashSelector?
    val focusMode: FocusModeSelector?
    val jpegQuality: QualitySelector?
    val exposureCompensation: ExposureSelector?
    val frameProcessor: FrameProcessor?
    val previewFpsRange: FpsRangeSelector?
    val antiBandingMode: AntiBandingModeSelector?
    val sensorSensitivity: SensorSensitivitySelector?
    val previewResolution: ResolutionSelector?
    val pictureResolution: ResolutionSelector?
}