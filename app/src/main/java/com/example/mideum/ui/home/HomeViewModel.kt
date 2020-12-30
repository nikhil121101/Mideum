package com.example.mideum.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mideum.models.domain.Article
import com.example.mideum.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>?>()
    val articles : LiveData<List<Article>?> = _articles

    private val _networkWorkDone = MutableLiveData<Boolean>()
    val networkWorkDone : LiveData<Boolean> = _networkWorkDone

    fun resetNetwork() {
        _networkWorkDone.value = false
    }

    fun getGlobalFeed() {
        viewModelScope.launch {
            val articlesResponse = Repository.getGlobalFeed()
            _articles.value = articlesResponse
            _networkWorkDone.value = true
        }
    }

    fun getMyFeed() {
        viewModelScope.launch {
            val articlesResponse = Repository.getMyFeed()
            _articles.value = articlesResponse
            _networkWorkDone.value = true
        }
    }

}