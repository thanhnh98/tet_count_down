package com.thanh_nguyen.test_count_down.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun encodeToBase64(image: Bitmap, quality: Int  = 100): String? {
    val bm: Bitmap = image
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.PNG, quality, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun decodeBase64(input: String?): Bitmap? {
    if (input == null)
        return  null
    val decodedByte: ByteArray = Base64.decode(input, 0)
    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
}