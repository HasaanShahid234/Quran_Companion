package com.example.qurancompanion.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qurancompanion.R
import com.example.qurancompanion.adapter.AyahAdapter
import com.example.qurancompanion.response_model.Ayah
import com.example.qurancompanion.response_model.Surah
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SurahDetailsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surah_details)

        sharedPreferences = getSharedPreferences("QuranData", MODE_PRIVATE)

        val surahNumber = intent.getIntExtra("surah_number", 0)
        val surahList = getSurahListFromPrefs()
        val selectedSurah = surahList.find { it.number == surahNumber }

        selectedSurah?.let {
            setupRecyclerView(it.ayahs)
        }
    }
    private fun setupRecyclerView(ayahs: List<Ayah>) {
        recyclerView = findViewById(R.id.recyclerViewDetails)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AyahAdapter(this,ayahs)
        recyclerView.adapter = adapter
    }

    private fun getSurahListFromPrefs(): List<Surah> {
        val json = sharedPreferences.getString("surah_list", null)
        val type = object : TypeToken<List<Surah>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }
}
