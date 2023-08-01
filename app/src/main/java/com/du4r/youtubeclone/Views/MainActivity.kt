package com.du4r.youtubeclone.Views

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.du4r.youtubeclone.Models.Video
import com.du4r.youtubeclone.R
import com.du4r.youtubeclone.Repositories.VideosRepository
import com.du4r.youtubeclone.Viewmodels.MainViewModel
import com.du4r.youtubeclone.Viewmodels.MainViewModelFactory
import com.du4r.youtubeclone.dataSource.RetrofitService
import com.du4r.youtubeclone.databinding.ActivityMainBinding
import com.du4r.youtubeclone.formatTime
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvMain: RecyclerView
    private lateinit var rvSimilar: RecyclerView
    private lateinit var viewLayer: View
    private lateinit var seekBar: SeekBar
    private lateinit var surfacePlayer: SurfaceView
    private lateinit var videoPlayer: ImageView
    private lateinit var viewModel: MainViewModel
    private var youtubePlayer: YoutubePlayer? = null
    private lateinit var adapter: VideoAdapter
    val videos = mutableListOf<Video>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        setupUi()

    }

    override fun onStart() {
        super.onStart()

        viewModel.videoList.observe(this, Observer { video ->
            videos.clear()
            videos.addAll(video.data.toMutableList())
            adapter.notifyDataSetChanged()
            binding.motionContainer.removeView(binding.progressRecycler)
            binding.progressRecycler.visibility = View.GONE
        })

        viewModel.errorMessage.observe(this, {error ->
            Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
        })

    }

    override fun onPause() {
        super.onPause()
        youtubePlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayer?.release()
    }

    private fun setupViewModel(){
        viewModel =
            ViewModelProvider(this,
                MainViewModelFactory(VideosRepository(RetrofitService.getInstance()))
            ).get(MainViewModel::class.java)

        viewModel.getVideos()
    }

    private fun setupUi() {

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = ""

        adapter = VideoAdapter(videos){video ->
            showOverlayView(video)
        }

        rvMain = binding.rvMain
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = adapter


        rvSimilar = findViewById(R.id.rv_similar)
        viewLayer = findViewById(R.id.view_layer)
        surfacePlayer = findViewById(R.id.surface_player)
        videoPlayer = findViewById(R.id.video_player)
        seekBar = findViewById(R.id.seek_bar)

        viewLayer.alpha = 0f

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    youtubePlayer?.seek(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        preparePlayer()
    }

    fun preparePlayer() {
        youtubePlayer = YoutubePlayer(this)
        youtubePlayer?.youtubePlayerListener = object : YoutubePlayer.YoutubePlayerListener {
            override fun onPrepared(duration: Int) {
            }

            override fun onTrackTime(currentPosition: Long, percent: Long) {
                seekBar.progress = percent.toInt()
                findViewById<TextView>(R.id.current_time).text = currentPosition.formatTime()
            }
        }
        surfacePlayer.holder.addCallback(youtubePlayer)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showOverlayView(video: Video) {
        viewLayer.animate().apply {
            duration = 400
            alpha(0.5f)
        }

        binding.motionContainer.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                if (progress > 0.5f)
                    viewLayer.alpha = 1.0f - progress
                else
                    viewLayer.alpha = 0.5f
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })

        videoPlayer?.visibility = View.GONE
        youtubePlayer?.setUrl(video.videoUrl)

        val detailAdapter = VideoDetailAdapter(videos)
        rvSimilar.layoutManager = LinearLayoutManager(this)
        rvSimilar.adapter = detailAdapter
        detailAdapter.notifyDataSetChanged()
    }


}