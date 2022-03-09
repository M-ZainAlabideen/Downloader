package com.downloader.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.downloader.factory.FilesViewModelFactory
import com.downloader.model.response.FileResponse
import com.downloader.repository.FilesRepo
import com.downloader.utils.Coroutines
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(private val repository: FilesRepo) : ViewModel() {

    private lateinit var job: Job

    private val _slider = MutableLiveData<ArrayList<FileResponse>>()
    val slider: LiveData<ArrayList<FileResponse>> get() = _slider
    var progressbarObservable = MutableLiveData(false)

    fun getFiles() {
        progressbarObservable.postValue(true)
        job = Coroutines.ioThenMain(
            { repository.getFiles() },
            {
                _slider.value = it!!
                progressbarObservable.postValue(false)
            }
        )

    }

    fun getProgress(): MutableLiveData<Boolean?> {
        return progressbarObservable
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }


}