package com.downloader.model.response


data class FileResponse(
    val id: Int,
    val name: String,
    val type: String,
    val url: String
)