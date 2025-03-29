package com.example.qurancompanion.utils

import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import com.example.qurancompanion.R

class AudioPlayerDialog(private val context: Context, private val audioUrl: String,private val title: String) {
    private val dialog: Dialog = Dialog(context)
    private var mediaPlayer: MediaPlayer? = null
    private var playing = false
    private val handler = Handler(Looper.getMainLooper())

    private var seekBar: SeekBar
    private var btnPauseResume: Button

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.audio_dialog, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvAudioName: TextView = view.findViewById(R.id.tvAudioName)
        btnPauseResume = view.findViewById(R.id.btnPauseResume)
        val btnClose: Button = view.findViewById(R.id.btnClose)
        seekBar = view.findViewById(R.id.seekBar)

        tvAudioName.text = "Playing: $title"

        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            setOnPreparedListener {
                seekBar.max = duration
                start()
                playing = true
                updateSeekBar()
            }
            setOnCompletionListener {
                btnPauseResume.text = "Play Again"
                playing = false
                seekBar.progress = 0
            }
            prepareAsync()
        }

        btnPauseResume.setOnClickListener {
            mediaPlayer?.let {
                if (playing) {
                    it.pause()
                    btnPauseResume.text = "Resume"
                } else {
                    it.start()
                    btnPauseResume.text = "Pause"
                    updateSeekBar()
                }
                playing = !playing
            }
        }

        btnClose.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = null
            dialog.dismiss()
        }

        // SeekBar drag functionality
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        seekBar.progress = it.currentPosition
                        handler.postDelayed(this, 500)
                    }
                }
            }
        }, 500)
    }

    fun show() {
        dialog.show()
        dialog.window?.setLayout(
            (context.resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
