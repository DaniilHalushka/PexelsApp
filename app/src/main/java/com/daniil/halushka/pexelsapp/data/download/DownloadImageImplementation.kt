package com.daniil.halushka.pexelsapp.data.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import com.daniil.halushka.pexelsapp.R

class DownloadImageImplementation(
    private val appContext: Context
) : DownloadImage {
    private val downloadManager = appContext.getSystemService(DownloadManager::class.java)

    override fun downloadFile(
        imageUrl: String,
        destinationFileName: String
    ): Long {
        val downloadRequest = buildDownloadRequest(imageUrl, destinationFileName)

        return try {
            showToastMessage(R.string.started_download)
            downloadManager.enqueue(downloadRequest)
        } catch (exception: Exception) {
            showToastMessage(R.string.failed_download, exception.message ?: "")
            -1
        }
    }

    private fun buildDownloadRequest(
        imageUrl: String,
        destinationFileName: String
    ): DownloadManager.Request {
        val fileName = "$destinationFileName.jpg"

        return DownloadManager.Request(imageUrl.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        or DownloadManager.Request.NETWORK_MOBILE
            )
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )
    }


    private fun showToastMessage(messageResId: Int, vararg formatArgs: Any) {
        val message = if (formatArgs.isEmpty()) {
            appContext.getString(messageResId)
        } else {
            appContext.getString(messageResId, *formatArgs)
        }
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }
}