package com.example.randomuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.randomuser.databinding.ActivityMainBinding
import com.example.randomuser.di.component.AppComponent
import com.example.randomuser.presentation.RandomUserApplication


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var component: AppComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        component = (application as RandomUserApplication).appComponent

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }




}