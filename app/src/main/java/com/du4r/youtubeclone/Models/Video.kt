package com.du4r.youtubeclone.Models

import com.du4r.youtubeclone.toDate
import com.google.gson.annotations.SerializedName
import java.util.*

data class Video(
    val id: String,
    val thumbnailUrl: String,
    val title: String,
    val publishedAt: Date,
    val viewsCount: Long,
    val viewsCountLabel: String,
    val duration: Int?,
    val videoUrl: String,
    val publisher: Publisher,
)

class VideoBuilder{
    val id: String = ""
    val thumbnailUrl: String = ""
    val title: String = ""
    val publishedAt: Date = Date()
    val viewsCount: Long = 0
    val viewsCountLabel: String = ""
    val duration: Int? = 0
    val videoUrl: String = ""
    val publisher: Publisher = PublisherBuilder().build()

    fun build(): Video = Video(
        id,thumbnailUrl,title,publishedAt,viewsCount,viewsCountLabel,duration,videoUrl,publisher
    )

    fun publisher(block: PublisherBuilder.() -> Unit): Publisher = PublisherBuilder().apply(block).build()
}


fun video(block: VideoBuilder.() -> Unit): Video = VideoBuilder().apply(block).build()

//fun videos(): List<Video>{
//    return DUMMY_VIDEOS
//}