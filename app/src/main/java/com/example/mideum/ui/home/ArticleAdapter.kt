package com.example.mideum.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mideum.databinding.ArticleCardBinding
import com.example.mideum.models.domain.Article
import com.example.mideum.models.domain.Profile

class ArticleAdapter(private val articleCardClickListener : ArticleCardClickListener) : ListAdapter<Article, ArticleViewHolder>(ArticleDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article , articleCardClickListener)
    }

}

class ArticleCardClickListener(val articleClick : (article : Article) -> Unit,
                               val profileClick : (profile : Profile) -> Unit) {
    fun onArticleClick(article : Article) = articleClick(article)
    fun onProfileClick(profile : Profile) = profileClick(profile)
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.slug == newItem.slug
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}

class ArticleViewHolder(private val binding : ArticleCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article : Article, articleCardClickListener: ArticleCardClickListener) {
        binding.article = article
        binding.clickListener = articleCardClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent : ViewGroup) : ArticleViewHolder {
            val layoutInflater =LayoutInflater.from(parent.context)
            val binding = ArticleCardBinding.inflate(layoutInflater , parent , false)
            return ArticleViewHolder(binding)
        }
    }

}