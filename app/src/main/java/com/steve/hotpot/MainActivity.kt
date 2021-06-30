package com.steve.hotpot

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.steve.hotpot.databinding.ActivityMainBinding
import com.steve.hotpot.ui.EntryFragment
import com.steve.hotpot.ui.ListFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val INTERNET_PERMISSION = 101
    val NETWORK_STATE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkForPermissions(android.Manifest.permission.INTERNET, "Internet", INTERNET_PERMISSION)
        checkForPermissions(android.Manifest.permission.ACCESS_NETWORK_STATE, "Network State", NETWORK_STATE)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_DENIED ->
                    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }
}