package com.example.codespace

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.codespace.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSplashScreenAnimation()
    }

    private fun setSplashScreenAnimation() {
        val video: VideoView = binding.vvSplashScreen
        val filePlace = "android.resource://" + packageName + "/raw/" + R.raw.splash_animation
        video.setVideoURI(Uri.parse(filePlace))
        video.start()
        video.setOnCompletionListener {
            onAnimationComplete()
        }
    }

    private fun onAnimationComplete() {
        val toOnboardingScreen = Intent(this, OnboardingActivity::class.java)
        startActivity(toOnboardingScreen)
        finish()
    }
}