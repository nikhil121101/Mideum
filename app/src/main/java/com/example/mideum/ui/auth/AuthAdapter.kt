package com.example.mideum.ui.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mideum.ui.auth.signIn.SignInFragment
import com.example.mideum.ui.auth.signUp.SignUpFragment

class AuthAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SignInFragment()
            else -> return SignUpFragment()
        }
    }
}