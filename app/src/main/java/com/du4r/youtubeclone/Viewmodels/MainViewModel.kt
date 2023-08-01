package com.du4r.youtubeclone.Viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.du4r.youtubeclone.Models.ListVideo
import com.du4r.youtubeclone.Repositories.VideosRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class MainViewModel(private val repository: VideosRepository): ViewModel(){

    val videoList = MutableLiveData<ListVideo>()
    val errorMessage = MutableLiveData<String>()

    fun getVideos(){
        viewModelScope.launch(Dispatchers.IO){
           val request = repository.getAllVideos()

            withContext(Dispatchers.Main){
                request.enqueue(object : Callback<ListVideo>{
                    override fun onResponse(call: Call<ListVideo>, response: Response<ListVideo>) {
                        if (response.code() == HTTP_OK){
                            videoList.postValue(response.body())
                        }else{
                            errorMessage.postValue("erro ao receber video ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<ListVideo>, t: Throwable) {
                        errorMessage.postValue(t.message)
                    }
                })
            }
        }
    }

}