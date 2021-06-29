package com.steve.hotshots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.steve.hotshots.databinding.ActivityMainBinding
import com.steve.hotshots.ui.EntryFragment
import com.steve.hotshots.ui.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val frag = intent.getStringExtra("frag")
        var targetFragment: Fragment? = null

        // when activity create
        showListFragment()
    }

    fun showListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment.newInstance())
            .commitNow()
    }

    fun showEntryFragment(index: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, EntryFragment.newInstance(index))
            .commitNow()
    }
}