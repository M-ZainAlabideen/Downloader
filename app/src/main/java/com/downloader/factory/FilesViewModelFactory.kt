package com.downloader.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.downloader.repository.FilesRepo
import com.downloader.view_model.FilesViewModel
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Suppress("UNCHECKED_CAST")
class FilesViewModelFactory ( private val repository: FilesRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilesViewModel(repository) as T
    }
}