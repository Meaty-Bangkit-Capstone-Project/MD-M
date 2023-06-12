package com.damar.meaty.etc

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

private const val MAXIMAL_SIZE = 1000000

fun reduceFileImage(file: File): File {
    val options = BitmapFactory.Options()
    options.inSampleSize = 1
    var bitmap = BitmapFactory.decodeFile(file.path, options)
    var compressQuality = 100
    var streamLength: Int
    while (true) {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        if (streamLength <= MAXIMAL_SIZE) {
            break
        }
        compressQuality -= 5
        options.inSampleSize *= 2
        bitmap = BitmapFactory.decodeFile(file.path, options)
    }
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}