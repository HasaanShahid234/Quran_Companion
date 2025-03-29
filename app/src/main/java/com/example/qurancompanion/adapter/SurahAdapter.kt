package com.example.qurancompanion.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qurancompanion.R
import com.example.qurancompanion.response_model.Surah
import com.example.qurancompanion.ui.SurahDetailsActivity
import com.example.qurancompanion.utils.AudioPlayerDialog

class SurahAdapter(
    private val context: Context,
    private val surahList: List<Surah>,
    private val onPlayClick: (String) -> Unit,
) : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return SurahViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val surah = surahList[position]
        holder.tvSurahName.text = surah.name

        holder.btnRead.setOnClickListener {
            val intent = Intent(context, SurahDetailsActivity::class.java)
            intent.putExtra("surah_number", surah.number)
            intent.putExtra("surah_name", surah.name)
            context.startActivity(intent)
        }

        holder.btnPlay.setOnClickListener {
            val firstAyahName = surah.name
            val firstAyahAudio = surah.ayahs.firstOrNull()?.audio
            if (!firstAyahAudio.isNullOrEmpty()) {
                val dialog = AudioPlayerDialog(context, firstAyahAudio,firstAyahName)
                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int = surahList.size

    class SurahViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSurahName: TextView = view.findViewById(R.id.tvSurahName)
        val btnRead: ImageView = view.findViewById(R.id.btnRead)
        val btnPlay: ImageView = view.findViewById(R.id.btnPlay)
    }
}
