package com.example.qurancompanion.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qurancompanion.response_model.QuranResponse
import kotlinx.coroutines.launch

class QuranViewModel : ViewModel() {
    private val repository = QuranRepository()

    private val _quranData = MutableLiveData<QuranResponse?>()
    val quranData: LiveData<QuranResponse?> = _quranData

    fun fetchQuran() {
        viewModelScope.launch {
            val response = repository.getQuran()
            if (response.isSuccessful) {
                _quranData.postValue(response.body())
            }
        }
    }
}