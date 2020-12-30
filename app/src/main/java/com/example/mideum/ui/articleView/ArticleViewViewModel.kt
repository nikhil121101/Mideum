package com.example.mideum.ui.articleView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mideum.models.domain.Article
import com.example.mideum.models.domain.Comment
import com.example.mideum.repository.Repository
import kotlinx.coroutines.launch
import java.net.ResponseCache

class ArticleViewViewModel : ViewModel() {

    private val _article = MutableLiveData<Article>()
    val article : LiveData<Article> = _article

    private val _commentsList = MutableLiveData<List<Comment>>()
    val commentsList : LiveData<List<Comment>> = _commentsList

    private val _listChanged = MutableLiveData<Boolean>()
    val listChanged : LiveData<Boolean> = _listChanged

    fun favouriteArticle(slug : String) {
        viewModelScope.launch {
            val res = Repository.favouriteArticle(slug)
            _article.value = res
        }
    }

    fun unFavouriteArticle(slug : String) {
        viewModelScope.launch {
            val res = Repository.unFavouriteArticle(slug)
            _article.value = res
        }
    }


    fun fetchComments(slug : String) {
        viewModelScope.launch {
            val res = Repository.getComments(slug)
            _commentsList.value = res
        }
    }

    fun deleteComment(slug : String , id : Int) {
        viewModelScope.launch {
            Repository.deleteComment(slug , id)
            _listChanged.value = true
        }
    }

    fun addComment(slug : String , text : String) {
        viewModelScope.launch {
            Repository.addComment(slug , text)
            _listChanged.value = true
        }
    }

    fun resetListChanged() {
        _listChanged.value = false
    }

}