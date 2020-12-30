package com.example.mideum.ui.profileView

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mideum.MainActivity
import com.example.mideum.databinding.ProfileFragmentBinding
import com.example.mideum.models.domain.Profile
import com.example.mideum.models.domain.toProfile
import com.example.mideum.ui.home.ArticleAdapter
import com.example.mideum.ui.home.ArticleCardClickListener
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.InvocationTargetException

class ProfileFragment : Fragment() {

    private val args : ProfileFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var profile : Profile
        profile = try {
            args.profile
        }
        catch (exp : InvocationTargetException) {
            MainActivity.user?.toProfile()!!
        }

        val binding =  ProfileFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.profile = profile

        //setting article card list on recycler view

        val articleCardClickListener = ArticleCardClickListener(
            {
                //on article click
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToArticleView(it))
            } ,
            {
                // on profile click
               findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentSelf(it))
            }
        )

        val adapter = ArticleAdapter(articleCardClickListener)

        binding.recyclerViewProfile.adapter = adapter

        //**

        viewModel.article.observe(viewLifecycleOwner , {
            adapter.submitList(it)
        })

        viewModel.stopLoadingBar.observe(viewLifecycleOwner , {
            when(it) {
                true -> binding.profileProgressBar.visibility = View.GONE
                else -> binding.profileProgressBar.visibility = View.VISIBLE
            }
        })

        viewModel.profile.observe(viewLifecycleOwner , {
            it?.let {
                profile = it
                binding.profile = it
            }
        })

        viewModel.getArticlesOf(profile.username)

        binding.tabLayoutProfile.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        viewModel.getArticlesOf(profile.username)
                    }
                    1 -> {
                        viewModel.getFavouriteArticlesOf(profile.username)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        if(MainActivity.user == null || profile.username == MainActivity.user!!.username) {
            binding.followButton.visibility = View.GONE
        }

        binding.followButton.setOnClickListener{
            if(profile.following)
                viewModel.unFollowProfile(profile.username)
            else
                viewModel.followProfile(profile.username)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}