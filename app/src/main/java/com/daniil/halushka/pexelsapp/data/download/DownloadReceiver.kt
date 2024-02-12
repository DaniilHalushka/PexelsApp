package com.daniil.halushka.pexelsapp.data.download

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.daniil.halushka.pexelsapp.R

class DownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val pictureId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            requireNotNull(context) { R.string.context_is_null }

            pictureId.takeIf { it != -1L }?.let {
                Toast.makeText(
                    context,
                    R.string.download_completed_message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
