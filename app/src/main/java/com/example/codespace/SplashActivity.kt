package com.example.codespace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.codespace.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSplashScreenAnimation()
        sharedPrefs =
            getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
            )
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
        val currentUsername: String? =
            sharedPrefs.getString(
                getString(R.string.prefs_username_key), null
            )
        println(currentUsername)
        val nextScreen: Intent = if (currentUsername == null || currentUsername == "") {
            Intent(this, OnboardingActivity::class.java)

        } else {
            Intent(this, HomeActivity::class.java)

        }
        startActivity(nextScreen)
        finish()
    }
}