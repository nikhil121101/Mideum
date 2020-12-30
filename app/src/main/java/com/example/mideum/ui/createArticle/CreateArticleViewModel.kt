package com.example.mideum.ui.createArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mideum.models.domain.Article
import com.example.mideum.repository.Repository
import kotlinx.coroutines.launch

enum class NetworkStatus{
    SUCCESSFUL , UNSUCCESSFUL
}

class CreateArticleViewModel : ViewModel() {

    private val _networkStatus  = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus> = _networkStatus

    private val _article = MutableLiveData<Article>()
    val article : LiveData<Article> = _article

    fun createArticle(title : String , description: String , body:String) {
        viewModelScope.launch {
            val article = Repository.createArticle(title , description , body)
            _article.value = article
            _networkStatus.value = if(article != null) NetworkStatus.SUCCESSFUL else NetworkStatus.UNSUCCESSFUL
        }
    }
}