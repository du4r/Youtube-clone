package com.du4r.youtubeclone.Models

data class Publisher(
    val id: String,
    val name: String,
    val pictureProfileUrl: String,
)

class PublisherBuilder{
    val id: String = ""
    val name: String = ""
    val pictureProfileUrl: String = ""

    fun build(): Publisher = Publisher(
        id,name,pictureProfileUrl
    )
}