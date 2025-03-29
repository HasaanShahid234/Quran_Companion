package com.example.qurancompanion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qurancompanion.R
import com.example.qurancompanion.response_model.Ayah

class AyahAdapter(private val ayahs: List<Ayah>) : RecyclerView.Adapter<AyahAdapter.AyahViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayah, parent, false)
        return AyahViewHolder(view)
    }

    override fun onBindViewHolder(holder: AyahViewHolder, position: Int) {
        val ayah = ayahs[position]
        holder.tvAyahNumber.text = "Ayah ${ayah.numberInSurah}"
        holder.tvArabicText.text = ayah.text
        holder.tvTranslation.text = ayah.translation
    }

    override fun getItemCount(): Int = ayahs.size

    class AyahViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAyahNumber: TextView = view.findViewById(R.id.tvAyahNumber)
        val tvArabicText: TextView = view.findViewById(R.id.tvArabicText)
        val tvTranslation: TextView = view.findViewById(R.id.tvTranslation)
    }
}