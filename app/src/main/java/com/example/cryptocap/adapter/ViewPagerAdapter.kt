package com.example.cryptocap.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptocap.fragment.CryptosFragment
import com.example.cryptocap.fragment.MoodFragment
import com.example.cryptocap.fragment.PortfolioFragment

class ViewPagerAdapter(
    activity: AppCompatActivity
    ): FragmentStateAdapter(activity)
{
    private var fragments : ArrayList<Fragment> = arrayListOf(
        CryptosFragment(),
        PortfolioFragment(),
        MoodFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}