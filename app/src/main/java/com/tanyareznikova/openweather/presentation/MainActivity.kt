package com.tanyareznikova.openweather.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tanyareznikova.openweather.R
import com.tanyareznikova.openweather.databinding.ActivityMainBinding
import com.tanyareznikova.openweather.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        //navController.navigate(R.id.searchFragment)

        //val navHostFragment = mBinding.fragmentContainerView.getFragment<NavHostFragment>().navController
        //navController = navHostFragment
    }

    override fun onResume() {
        super.onResume()

        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation) as NavHostFragment
        //navController = navHostFragment.navController

    }
}