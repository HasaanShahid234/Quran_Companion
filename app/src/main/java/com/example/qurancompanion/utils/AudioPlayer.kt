package com.example.qurancompanion.utils

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(context: Context, audioUrl: String) {
        stopAudio() // Pehle se koi audio chal rahi ho to usko stop karo

        Log.d("AudioPlayer", "Playing URL: $audioUrl") // Debugging ke liye

        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            setOnPreparedListener { start() }
            setOnErrorListener { _, what, extra ->
                Log.e("AudioPlayer", "Error: $what, Extra: $extra") // Error logging
                false
            }
            prepareAsync()
        }
    }

    fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
