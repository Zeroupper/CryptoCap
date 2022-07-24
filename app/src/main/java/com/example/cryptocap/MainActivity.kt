package com.example.cryptocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptocap.adapter.ViewPagerAdapter
import com.example.cryptocap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter : ViewPagerAdapter? = null
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0) {
            super.onBackPressed()
        }else {
            viewPager.currentItem--
        }
    }
}