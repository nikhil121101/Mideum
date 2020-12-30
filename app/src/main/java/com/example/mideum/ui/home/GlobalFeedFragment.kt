package com.example.mideum.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mideum.databinding.FeedFragmentGlobalBinding

class GlobalFeedFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val binding = FeedFragmentGlobalBinding.inflate(layoutInflater, container, false)

        val articleCardClickListener = ArticleCardClickListener(
                {
                    //on article click
                    findNavController().navigate(GlobalFeedFragmentDirections.actionGlobalFeedFragmentToArticleView(it))
                } ,
                {
                    // on profile click
                    findNavController().navigate(GlobalFeedFragmentDirections.actionGlobalFeedFragmentToProfileFragment(it))
                }
        )

        val adapter = ArticleAdapter(articleCardClickListener)


        binding.globalFeedRV.adapter = adapter

        val mDividerItemDecoration = DividerItemDecoration(
                binding.globalFeedRV.context , DividerItemDecoration.VERTICAL)

        binding.globalFeedRV.addItemDecoration(mDividerItemDecoration)

        homeViewModel.articles.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        homeViewModel.networkWorkDone.observe(viewLifecycleOwner , {
            if(it) {
                binding.globalLoadingBar.isVisible = false
                binding.globalNoFeedText.visibility = if(homeViewModel.articles.value.isNullOrEmpty())
                    View.VISIBLE else View.GONE
                homeViewModel.resetNetwork()
            }
        })

        homeViewModel.getGlobalFeed()

        return binding.root
    }
}