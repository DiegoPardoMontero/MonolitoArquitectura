package com.puj.proyectoensenarte.util

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
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
        inflate(context, R.layout.custom_video_view, this)
        videoView = findViewById(R.id.videoView)
        controllerContainer = findViewById(R.id.controllerContainer)

        mediaController = object : MediaController(context) {
            override fun show(timeout: Int) {
                super.show(timeout)
                visibility = View.VISIBLE
            }

            override fun hide() {
                super.hide()
                visibility = View.GONE
            }
        }
    }

    fun setupVideo(url: String?) {
        if (url != null) {
            videoView.setVideoURI(Uri.parse(url))
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(controllerContainer)

            videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
                addMediaControllerToContainer()
                mediaController.show(0)  // Mostrar los controles inmediatamente
                videoView.start()  // Iniciar el video
                videoView.pause()  // Pausar inmediatamente para que el usuario pueda iniciar cuando quiera
            }
        }
    }

    private fun addMediaControllerToContainer() {
        controllerContainer.removeAllViews()

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