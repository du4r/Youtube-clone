package com.du4r.youtubeclone.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.du4r.youtubeclone.Repositories.VideosRepository

class MainViewModelFactory(private val repository: VideosRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}