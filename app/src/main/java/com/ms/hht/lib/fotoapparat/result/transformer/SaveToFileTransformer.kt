package com.ms.hht.lib.fotoapparat.result.transformer

import com.ms.hht.lib.fotoapparat.exception.FileSaveException
import com.ms.hht.lib.fotoapparat.exif.ExifOrientationWriter
import com.ms.hht.lib.fotoapparat.result.Photo
import java.io.*

/**
 * Saves [Photo] to file.
 */
internal class SaveToFileTransformer(
        private val file: File,
        private val exifOrientationWriter: ExifOrientationWriter
) : (Photo) -> Unit {

    override fun invoke(input: Photo) {
        val outputStream = try {
            FileOutputStream(file).buffered()
        } catch (e: FileNotFoundException) {
            throw FileSaveException(e)
        }

        try {
            saveImage(input, outputStream)

            exifOrientationWriter.writeExifOrientation(file, input.rotationDegrees)
        } catch (e: IOException) {
            throw FileSaveException(e)
        }
    }
}

@Throws(IOException::class)
private fun saveImage(input: Photo, outputStream: BufferedOutputStream) {
    outputStream.use {
        it.write(input.encodedImage)
        it.flush()
    }
}