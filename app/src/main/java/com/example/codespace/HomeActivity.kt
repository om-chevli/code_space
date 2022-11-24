package com.example.codespace

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codespace.databinding.ActivityHomeBinding
import com.example.codespace.databinding.ActivitySplashBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val user:UserData = UserData.getInstance()
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs =
            getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
            )
            user.updateUserDataFromPrefs(sharedPrefs)

        startActivity(Intent(this, ViewLessonsActivity::class.java))

    }
}