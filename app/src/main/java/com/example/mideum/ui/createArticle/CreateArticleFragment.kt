package com.example.mideum.ui.createArticle
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.mideum.databinding.CreateArticleFragmentBinding

class CreateArticleFragment : Fragment() {

    private lateinit var viewModel: CreateArticleViewModel
    private lateinit var binding : CreateArticleFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = CreateArticleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateArticleViewModel::class.java)

        viewModel.article.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(CreateArticleFragmentDirections.actionCreateArticleFragmentToArticleView(it))
            }
        }

        viewModel.networkStatus.observe(viewLifecycleOwner) {
            when(it) {
                NetworkStatus.SUCCESSFUL -> {
                    Toast.makeText(context , "Article created!" , Toast.LENGTH_SHORT).show()
                }
                NetworkStatus.UNSUCCESSFUL -> {
                    Toast.makeText(context , "Network error!!" , Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }

        binding.publishButton.setOnClickListener {
            if(binding.articleTitleCreate.text.toString().isNotBlank() && binding.bodyCreate.text.toString().isNotBlank()) {
                viewModel.createArticle(
                    binding.articleTitleCreate.text.toString() ,
                    binding.articleDescriptionCreate.text.toString() ,
                    binding.bodyCreate.text.toString() ,
                )
            }
        }

    }

}