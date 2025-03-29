package com.example.qurancompanion.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qurancompanion.R
import com.example.qurancompanion.adapter.SurahAdapter
import com.example.qurancompanion.data.QuranViewModel
import com.example.qurancompanion.response_model.QuranResponse
import com.example.qurancompanion.response_model.Surah
import com.example.qurancompanion.utils.AudioPlayer
import com.example.qurancompanion.utils.RetrofitInstance
import com.google.gson.Gson
import okhttp3.Callback
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private val viewModel: QuranViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var audioPlayer: AudioPlayer
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        audioPlayer = AudioPlayer()
        sharedPreferences = getSharedPreferences("QuranData", MODE_PRIVATE)

        viewModel.fetchQuran()

        viewModel.quranData.observe(this) { quranResponse ->
            quranResponse?.data?.surahs?.let { surahs ->
                saveSurahList(surahs)
                recyclerView.adapter = SurahAdapter(this, surahs) { audioUrl ->
                    audioPlayer.playAudio(this, audioUrl)
                }
            }
        }
    }

    private fun saveSurahList(surahList: List<Surah>) {
        sharedPreferences.edit() {
            val json = Gson().toJson(surahList)
            putString("surah_list", json)
        }
    }
}