package com.du4r.youtubeclone.Views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.du4r.youtubeclone.Formatted
import com.du4r.youtubeclone.Models.Video
import com.du4r.youtubeclone.R
import com.squareup.picasso.Picasso

class VideoDetailAdapter(private val videos: List<Video>) :
    RecyclerView.Adapter<VideoDetailAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.video_detail_list_item_video,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(video: Video) {
            with(itemView) {

                Picasso.get().load(video.thumbnailUrl)
                    .into(findViewById<ImageView>(R.id.video_thumbnail))

                //todo: CARREGAR IMAGEM DO AUTOR
//                Picasso.get().load(video.publisher.pictureProfileUrl)
//                    .into(findViewById<ImageView>(R.id.img_channel))


                findViewById<TextView>(R.id.video_title).text = video.title
                findViewById<TextView>(R.id.video_info).text = context.getString(
                    R.string.info,
                    video.publisher.name,
                    video.viewsCountLabel,
                    video.publishedAt.Formatted()
                )
            }
        }
    }
}