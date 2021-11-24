package info.rashadtanjim.core.utlis

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

fun Context.isConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            //It will check for both wifi and cellular network
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
        return false
    } else {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}

fun Context.hasStoragePermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        permission == PackageManager.PERMISSION_GRANTED
    } else true
}

fun Context.isNight(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (currentHour <= 7 || currentHour >= 18)
}

fun Context.saveToSdCard(bitmap: Bitmap): String? {
    val filename = "PICSUM_PHOTO_${System.currentTimeMillis()}"
    var stored: String? = null
    val sdcard: File = Environment.getExternalStorageDirectory()

    val folder = File(
        sdcard.absoluteFile,
        "Picsum"
    )
    folder.mkdir()
    val file = File(folder.absoluteFile, "$filename.jpg")
    if (file.exists()) return stored
    try {
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        stored = "success"
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return stored
}

//Make sure to call this function on a worker thread, else it will block main thread
fun Context.saveImageInQ(bitmap: Bitmap): Uri? {
    val filename = "PICSUM_PHOTO_${System.currentTimeMillis()}"
    var fos: OutputStream? = null
    var imageUri: Uri? = null

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //this one
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
    }

    //use application context to get contentResolver
    val contentResolver = this.contentResolver

    contentResolver.also { resolver ->
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { resolver.openOutputStream(it) }
    }

    fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }

    contentValues.clear()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
    }
    imageUri?.let { contentResolver.update(it, contentValues, null, null) }

    return imageUri
}