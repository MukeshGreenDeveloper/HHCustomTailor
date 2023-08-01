package com.ms.hht.lib.fotoapparat.result

import android.graphics.Bitmap
import com.ms.hht.lib.fotoapparat.exception.FileSaveException
import com.ms.hht.lib.fotoapparat.exif.ExifWriter
import com.ms.hht.lib.fotoapparat.log.Logger
import com.ms.hht.lib.fotoapparat.result.transformer.BitmapPhotoTransformer
import com.ms.hht.lib.fotoapparat.result.transformer.ResolutionTransformer
import com.ms.hht.lib.fotoapparat.result.transformer.SaveToFileTransformer
import com.ms.hht.lib.fotoapparat.result.transformer.originalResolution
import java.io.File
import java.util.concurrent.Future

/**
 * Result of taking the photo.
 */
class PhotoResult internal constructor(private val pendingResult: PendingResult<Photo>) {

    /**
     * Converts result to [Bitmap] of size provided by the [sizeTransformer].
     *
     * @param sizeTransformer Given the original size of the photo, returns the updated size so that
     * photo will be downscaled, upscaled or unchanged.
     * @return result as pending [BitmapPhoto] which will be available at some point in the
     * future.
     */
    @JvmOverloads
    fun toBitmap(
            sizeTransformer: ResolutionTransformer = originalResolution()
    ): PendingResult<BitmapPhoto> =
            pendingResult.transform(BitmapPhotoTransformer(sizeTransformer))

    /**
     * Saves result to file.
     *
     * @return pending operation which completes when photo is saved to file.
     * @throws FileSaveException If the file cannot be saved.
     */
    fun saveToFile(file: File): PendingResult<Unit> =
            pendingResult.transform(SaveToFileTransformer(
                    file = file,
                    exifOrientationWriter = ExifWriter
            ))

    /**
     * @return result as [PendingResult].
     */
    fun toPendingResult(): PendingResult<Photo> = pendingResult

    companion object {

        /**
         * Creates a new instance of advanced result from a Future result.
         *
         * @param photoFuture The future result of a [Photo].
         * @return The result.
         */
        internal fun fromFuture(
                photoFuture: Future<Photo>,
                logger: Logger
        ) = PhotoResult(
                PendingResult.fromFuture(photoFuture, logger)
        )

    }

}