package com.example.qurancompanion.response_model

data class QuranResponse(
    val data: QuranData
)

data class QuranData(
    val surahs: List<Surah>
)

data class Surah(
    val name: String,   // Surah name
    val number: Int,    // Surah number
    val ayahs: List<Ayah>
)

data class Ayah(
    val numberInSurah: Int,   // Ayah number in Surah
    val text: String,         // Arabic text
    val audio: String,        // Audio URL
    val translation: String   // English translation
)
