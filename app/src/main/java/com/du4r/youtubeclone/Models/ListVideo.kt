package com.du4r.youtubeclone.Models

import com.google.gson.annotations.SerializedName

data class ListVideo(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: List<Video>
)
