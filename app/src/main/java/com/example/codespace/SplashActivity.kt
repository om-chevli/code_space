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
    private var user: UserData = UserData.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefs =
            getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
            )
        setSplashScreenAnimation()
    }

    private fun setValuesToDataSource() {
        user.updateUserDataFromPrefs(sharedPrefs)
    }

    private fun setSplashScreenAnimation() {
        val video: VideoView = binding.vvSplashScreen
        val filePlace = "android.resource://" + packageName + "/raw/" + R.raw.splash_animation
        video.setVideoURI(Uri.parse(filePlace))
        video.start()
        video.setOnCompletionListener {
            onAnimationComplete()
        }
        setValuesToDataSource()
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