package com.daniil.halushka.pexelsapp.data.download

interface DownloadImage {
    fun downloadFile(
        imageUrl: String,
        destinationFileName: String
    ): Long
}