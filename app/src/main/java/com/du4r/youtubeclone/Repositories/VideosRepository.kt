package com.du4r.youtubeclone.Repositories

import com.du4r.youtubeclone.dataSource.RetrofitService

class VideosRepository(private val retrofitService: RetrofitService) {

    fun getAllVideos() = retrofitService.getVideos()

}