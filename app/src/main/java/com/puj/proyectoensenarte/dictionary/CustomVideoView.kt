package com.puj.proyectoensenarte.dictionary

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import com.puj.proyectoensenarte.R

class CustomVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val videoView: VideoView
    private val mediaController: MediaController
    private val controllerContainer: FrameLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_video_view, this, true)

        videoView = findViewById(R.id.videoView)
        controllerContainer = findViewById(R.id.controllerContainer)

        mediaController = object : MediaController(context) {
            override fun show() {
                super.show(0)
            }

            override fun hide() {
                // No hacer nada, mantener siempre visible
            }
        }

        // No establecer el anchorView aquí
        // mediaController.setAnchorView(controllerContainer)
    }

    fun setupVideo(url: String?) {
        if (url != null) {
            videoView.setVideoURI(Uri.parse(url))
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(controllerContainer) // Mover aquí
            videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
                videoView.pause()
                videoView.seekTo(1)
                addMediaControllerToContainer()
            }
        }
    }

    private fun addMediaControllerToContainer() {
        controllerContainer.removeAllViews()
        mediaController.visibility = View.VISIBLE

        // Remover el MediaController de su padre actual si lo tiene
        val parent = mediaController.parent as? ViewGroup
        parent?.removeView(mediaController)

        val lp = FrameLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )
        controllerContainer.addView(mediaController, lp)
    }
}