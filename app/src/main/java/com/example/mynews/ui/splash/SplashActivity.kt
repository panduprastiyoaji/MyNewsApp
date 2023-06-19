package com.example.mynews.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mynews.MainActivity
import com.example.mynews.databinding.ActivitySplashBinding
import com.example.mynews.di.GlobalSingleton
import com.example.mynews.di.GlobalSingletonListener
class SplashActivity : AppCompatActivity() {
    private val listener = Listener()
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000.toLong())
    }

    override fun onStart() {
        super.onStart()
        GlobalSingleton.register(listener)
    }

    override fun onStop() {
        super.onStop()
        GlobalSingleton.unregister(listener)
    }

    private inner class Listener : GlobalSingletonListener {
        override fun onEvent() {}
    }
}