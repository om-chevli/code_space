package com.example.codespace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.codespace.databinding.ActivityDoLessonBinding
import com.google.android.material.snackbar.Snackbar

class DoLessonActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoLessonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val user:UserData = UserData.getInstance()

        if (user.activeLesson != null){
            binding.textviewLessonTopic.text = "${user.getLessonNumber(user.activeLesson!!)}. ${user.activeLesson!!.topic}"
            binding.textviewLessonLength.text = "Length: ${user.convertLessonLength(user.activeLesson!!.lengthInMin)}"
            binding.textviewLessonDescription.text = user.activeLesson!!.description
            binding.buttonLessonVideo.setOnClickListener {
                //play video
                binding.webviewVideoPlayer.visibility = View.VISIBLE
                binding.webviewVideoPlayer.webViewClient = WebViewClient()
                binding.webviewVideoPlayer.settings.javaScriptEnabled = true
                binding.webviewVideoPlayer.settings.javaScriptCanOpenWindowsAutomatically = true
                binding.webviewVideoPlayer.settings.pluginState = WebSettings.PluginState.ON
                binding.webviewVideoPlayer.settings.mediaPlaybackRequiresUserGesture = false
                binding.webviewVideoPlayer.webChromeClient = WebChromeClient()
                binding.webviewVideoPlayer.loadUrl(user.activeLesson!!.videoLink)
            }
            binding.edittextLessonNotes.setText(user.activeLesson!!.notes)
            binding.buttonUpdateNotes.setOnClickListener {
                user.activeLesson!!.notes = binding.edittextLessonNotes.text.toString()
                Snackbar.make(binding.root, "Notes Saved!", Snackbar.LENGTH_SHORT).show()
            }
            binding.buttonLessonCompleted.setOnClickListener {
                user.activeLesson!!.isComplete = true
                user.lessons[user.lessons.indexOf(user.activeLesson)] = user.activeLesson!!
                user.activeLesson = null
                finish()
            }
        }
        else{
            finish()
        }
    }
}