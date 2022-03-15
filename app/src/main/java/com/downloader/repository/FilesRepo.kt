package com.downloader.repository

import com.downloader.network.ApiInterface
import com.downloader.utils.SafeApiRequest
import javax.inject.Inject

class FilesRepo (private val api : ApiInterface) : SafeApiRequest(){

    suspend fun getFiles() = apiRequest {
        api.getFiles()
    }

}