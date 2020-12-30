package com.example.mideum.ui.articleView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mideum.MainActivity
import com.example.mideum.databinding.ArticleViewFragmentBinding
import com.example.mideum.models.domain.Article
import com.example.mideum.ui.articleView.commentSection.CommentClickListener
import com.example.mideum.ui.articleView.commentSection.CommentsAdapter

class ArticleViewFragment : Fragment() {

    private val args : ArticleViewFragmentArgs by navArgs()

    private lateinit var viewModel: ArticleViewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = ArticleViewFragmentBinding.inflate(layoutInflater , container , false)

        viewModel = ViewModelProvider(this).get(ArticleViewViewModel::class.java)

        binding.commentEditText.isVisible = MainActivity.user != null
        binding.postCommentButtom.isVisible = MainActivity.user != null

        var article : Article = args.articleArg

        binding.article = article

        binding.imageView.setOnClickListener {
            findNavController().navigate(ArticleViewFragmentDirections.actionArticleViewToProfileFragment(article.author))
        }

        binding.usernameView.setOnClickListener {
            findNavController().navigate(ArticleViewFragmentDirections.actionArticleViewToProfileFragment(article.author))
        }

        binding.favouritArticle.setOnClickListener {
            if(article.favorited) {
                viewModel.unFavouriteArticle(article.slug)
            }
            else {
                viewModel.favouriteArticle(article.slug)
            }
        }

        viewModel.article.observe(viewLifecycleOwner , {
            if(it != null) {
                article = it
                binding.article = it
            }
        })

        val adapter = CommentsAdapter(CommentClickListener {
            viewModel.deleteComment(article.slug , it.id)
        })

        binding.recyclerViewComments.adapter = adapter

        viewModel.commentsList.observe(viewLifecycleOwner , {
            Log.i("articleViewModel" , "list updated${it.size}")
            adapter.submitList(it)
        })

        viewModel.listChanged.observe(viewLifecycleOwner , {
            if(it) {
                viewModel.fetchComments(article.slug)
                viewModel.resetListChanged()
            }
        })

        viewModel.fetchComments(article.slug)

        binding.postCommentButtom.setOnClickListener {
            viewModel.addComment(article.slug , binding.commentEditText.text.toString())
        }

        return binding.root

    }

}