package com.example.qurancompanion.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.qurancompanion.R
import com.example.qurancompanion.response_model.Ayah
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

class AyahAdapter(private val context: Context, private val ayahs: List<Ayah>) : RecyclerView.Adapter<AyahAdapter.AyahViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("bookmarks", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val bookmarks = loadBookmarks()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayah, parent, false)
        return AyahViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AyahViewHolder, position: Int) {
        val ayah = ayahs[position]
        holder.tvAyahNumber.text = "Ayah ${ayah.numberInSurah}"
        holder.tvArabicText.text = ayah.text
        holder.tvTranslation.text = ayah.translation

        holder.btnBookmark.setOnClickListener {
            toggleBookmark(ayah, holder.btnBookmark)
        }
        updateBookmarkIcon(holder.btnBookmark, ayah)
    }

    override fun getItemCount(): Int = ayahs.size

    class AyahViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAyahNumber: TextView = view.findViewById(R.id.tvAyahNumber)
        val tvArabicText: TextView = view.findViewById(R.id.tvArabicText)
        val tvTranslation: TextView = view.findViewById(R.id.tvTranslation)
        val btnBookmark: ImageView = view.findViewById(R.id.btnBookmark)
    }

    private fun updateBookmarkIcon(btnBookmark: ImageView, ayah: Ayah) {
        if (bookmarks.any { it.numberInSurah == ayah.numberInSurah }) {
            btnBookmark.setImageResource(R.drawable.baseline_bookmark_24)
        } else {
            btnBookmark.setImageResource(R.drawable.baseline_bookmark_border_24)
        }
    }

    private fun toggleBookmark(ayah: Ayah, btnBookmark: ImageView) {
        val existingAyah = bookmarks.find { it.numberInSurah == ayah.numberInSurah }

        if (existingAyah != null) {
            bookmarks.remove(existingAyah)
        } else {
            bookmarks.add(ayah)
        }

        saveBookmarks()
        updateBookmarkIcon(btnBookmark, ayah)
        notifyDataSetChanged()
    }

    private fun saveBookmarks() {
        val json = gson.toJson(bookmarks)
        sharedPreferences.edit {
            putString("bookmarked_ayahs", json)
        }
    }

    fun loadBookmarks(): MutableList<Ayah> {
        val json = sharedPreferences.getString("bookmarked_ayahs", "[]")
        return try {
            gson.fromJson(json, object : TypeToken<MutableList<Ayah>>() {}.type) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}
