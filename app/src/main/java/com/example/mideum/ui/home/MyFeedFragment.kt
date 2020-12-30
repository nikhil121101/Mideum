package com.example.mideum.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mideum.databinding.FeedFragmentMyFeedBinding

class MyFeedFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val binding = FeedFragmentMyFeedBinding.inflate(layoutInflater, container, false)

        val articleCardClickListener = ArticleCardClickListener(
            {
                //on article click
                findNavController().navigate(MyFeedFragmentDirections.actionMyFeedFragmentToArticleView(it))
            } ,
            {
                // on profile click
                findNavController().navigate(MyFeedFragmentDirections.actionMyFeedFragmentToProfileFragment(it))
            }
        )

        val adapter = ArticleAdapter(articleCardClickListener)


        binding.myFeedRV.adapter = adapter

        val mDividerItemDecoration = DividerItemDecoration(
            binding.myFeedRV.context , DividerItemDecoration.VERTICAL)

        binding.myFeedRV.addItemDecoration(mDividerItemDecoration)

        homeViewModel.articles.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        homeViewModel.networkWorkDone.observe(viewLifecycleOwner , {
            if(it) {
                binding.myLoadingBar.isVisible = false
                binding.myNoFeedText.visibility = if(homeViewModel.articles.value.isNullOrEmpty())
                    View.VISIBLE else View.GONE
                homeViewModel.resetNetwork()
            }
        })

        homeViewModel.getMyFeed()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(MyFeedFragmentDirections.actionNavMyFeedToCreateArticleFragment())
        }

        return binding.root
    }

}