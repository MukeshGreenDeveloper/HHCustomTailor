@file:Suppress("DEPRECATION")

package com.ms.hht.lib.fotoapparat.hardware

import android.hardware.Camera
import com.ms.hht.lib.fotoapparat.characteristic.getCharacteristics
import com.ms.hht.lib.fotoapparat.concurrent.CameraExecutor
import com.ms.hht.lib.fotoapparat.configuration.CameraConfiguration
import com.ms.hht.lib.fotoapparat.configuration.Configuration
import com.ms.hht.lib.fotoapparat.exception.camera.UnsupportedLensException
import com.ms.hht.lib.fotoapparat.hardware.display.Display
import com.ms.hht.lib.fotoapparat.hardware.orientation.Orientation
import com.ms.hht.lib.fotoapparat.log.Logger
import com.ms.hht.lib.fotoapparat.parameter.ScaleType
import com.ms.hht.lib.fotoapparat.parameter.camera.CameraParameters
import com.ms.hht.lib.fotoapparat.parameter.camera.provide.getCameraParameters
import com.ms.hht.lib.fotoapparat.selector.LensPositionSelector
import com.ms.hht.lib.fotoapparat.util.FrameProcessor
import com.ms.hht.lib.fotoapparat.view.CameraRenderer
import com.ms.hht.lib.fotoapparat.view.FocalPointSelector
import kotlinx.coroutines.CompletableDeferred

/**
 * Phone.
 */
internal open class Device(
        internal open val logger: Logger,
        private val display: Display,
        internal open val scaleType: ScaleType,
        internal open val cameraRenderer: CameraRenderer,
        internal val focusPointSelector: FocalPointSelector?,
        internal val executor: CameraExecutor,
        numberOfCameras: Int = Camera.getNumberOfCameras(),
        initialConfiguration: CameraConfiguration, initialLensPositionSelector: LensPositionSelector
) {

    private val cameras = (0 until numberOfCameras).map { cameraId ->
        CameraDevice(
                logger = logger,
                characteristics = getCharacteristics(cameraId)
        )
    }

    private var lensPositionSelector: LensPositionSelector = initialLensPositionSelector
    private var selectedCameraDevice = CompletableDeferred<CameraDevice>()
    private var savedConfiguration = CameraConfiguration.default()

    init {
        updateLensPositionSelector(initialLensPositionSelector)
        savedConfiguration = initialConfiguration
    }

    /**
     * Selects a camera.
     */
    open fun canSelectCamera(lensPositionSelector: LensPositionSelector): Boolean {
        val selectedCameraDevice = selectCamera(
                availableCameras = cameras,
                lensPositionSelector = lensPositionSelector
        )
        return selectedCameraDevice != null
    }

    /**
     * Selects a camera. Will do nothing if camera cannot be selected.
     */
    open fun selectCamera() {
        logger.recordMethod()

        selectCamera(
                availableCameras = cameras,
                lensPositionSelector = lensPositionSelector
        )
                ?.let(selectedCameraDevice::complete)
                ?: selectedCameraDevice.completeExceptionally(UnsupportedLensException())
    }

    /**
     * Clears the selected camera.
     */
    open fun clearSelectedCamera() {
        selectedCameraDevice = CompletableDeferred()
    }

    /**
     * Waits and returns the selected camera.
     */
    open suspend fun awaitSelectedCamera(): CameraDevice = selectedCameraDevice.await()

    /**
     * Returns the selected camera.
     *
     * @throws IllegalStateException If no camera has been yet selected.
     * @throws UnsupportedLensException If no camera could get selected.
     */
    open fun getSelectedCamera(): CameraDevice = try {
        selectedCameraDevice.getCompleted()
    } catch (e: IllegalStateException) {
        throw IllegalStateException("Camera has not started!")
    }

    /**
     * @return `true` if a camera has been selected.
     */
    open fun hasSelectedCamera() = selectedCameraDevice.isCompleted

    /**
     * @return Orientation of the screen.
     */
    open fun getScreenOrientation(): Orientation {
        return display.getOrientation()
    }

    /**
     * Updates the desired from the user camera lens position.
     */
    open fun updateLensPositionSelector(newLensPosition: LensPositionSelector) {
        logger.recordMethod()

        lensPositionSelector = newLensPosition
    }

    /**
     * Updates the desired from the user selectors.
     */
    open fun updateConfiguration(newConfiguration: Configuration) {
        logger.recordMethod()

        savedConfiguration = updateConfiguration(
                savedConfiguration = savedConfiguration,
                newConfiguration = newConfiguration
        )
    }

    /**
     * @return The desired from the user selectors.
     */
    open fun getConfiguration(): CameraConfiguration = savedConfiguration

    /**
     * @return The selected [CameraParameters] for the given [CameraDevice].
     */
    open suspend fun getCameraParameters(cameraDevice: CameraDevice): CameraParameters =
            getCameraParameters(
                    cameraConfiguration = savedConfiguration,
                    capabilities = cameraDevice.getCapabilities()
            )

    /**
     * @return The frame processor.
     */
    open fun getFrameProcessor(): FrameProcessor? = savedConfiguration.frameProcessor

    /**
     * @return The desired from the user camera lens position.
     */
    open fun getLensPositionSelector(): LensPositionSelector = lensPositionSelector

}

/**
 * Updates the device's configuration.
 */
internal fun updateConfiguration(
        savedConfiguration: CameraConfiguration,
        newConfiguration: Configuration
) = CameraConfiguration(
        flashMode = newConfiguration.flashMode ?: savedConfiguration.flashMode,
        focusMode = newConfiguration.focusMode ?: savedConfiguration.focusMode,
        exposureCompensation = newConfiguration.exposureCompensation
                ?: savedConfiguration.exposureCompensation,
        frameProcessor = newConfiguration.frameProcessor ?: savedConfiguration.frameProcessor,
        previewFpsRange = newConfiguration.previewFpsRange ?: savedConfiguration.previewFpsRange,
        sensorSensitivity = newConfiguration.sensorSensitivity
                ?: savedConfiguration.sensorSensitivity,
        pictureResolution = newConfiguration.pictureResolution
                ?: savedConfiguration.pictureResolution,
        previewResolution = newConfiguration.previewResolution
                ?: savedConfiguration.previewResolution
)

/**
 * Selects a camera from the set of available ones.
 */
internal fun selectCamera(
        availableCameras: List<CameraDevice>,
        lensPositionSelector: LensPositionSelector
): CameraDevice? {

    val lensPositions = availableCameras.map { it.characteristics.lensPosition }.toSet()
    val desiredPosition = lensPositionSelector(lensPositions)

    return availableCameras.find { it.characteristics.lensPosition == desiredPosition }
}
