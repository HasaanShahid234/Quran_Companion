package com.example.qurancompanion.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qurancompanion.R
import com.example.qurancompanion.adapter.AyahAdapter
import com.example.qurancompanion.response_model.Ayah
import com.example.qurancompanion.response_model.Surah
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookmarkActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AyahAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        recyclerView = findViewById(R.id.recyclerViewBookmarks)

        sharedPreferences = getSharedPreferences("bookmarks", MODE_PRIVATE)
        val bookmarkedAyahs = loadBookmarkedAyahs()

        adapter = AyahAdapter(this, bookmarkedAyahs)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadBookmarkedAyahs(): MutableList<Ayah> {
        val json = sharedPreferences.getString("bookmarked_ayahs", "[]")
        return try {
            gson.fromJson(json, object : TypeToken<MutableList<Ayah>>() {}.type) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}
