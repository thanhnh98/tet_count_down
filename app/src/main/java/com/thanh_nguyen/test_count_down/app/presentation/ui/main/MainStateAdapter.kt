package com.thanh_nguyen.test_count_down.app.presentation.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainStateAdapter(
    private val activity: FragmentActivity,
    private val fragments: List<MainStateModel>
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }
}

data class MainStateModel(
    val title: String,
    val fragment: Fragment

)