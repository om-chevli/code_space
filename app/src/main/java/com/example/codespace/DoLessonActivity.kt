package com.example.codespace

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.getSystemService
import com.example.codespace.databinding.ActivityDoLessonBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.internal.ViewUtils.requestFocusAndShowKeyboard
import com.google.android.material.snackbar.Snackbar

class DoLessonActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoLessonBinding
    private val user: UserData = UserData.getInstance()
    var startedVideo = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @SuppressLint("RestrictedApi")
    override fun onStart() {
        super.onStart()

        val sharedPrefs =
            getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
            )

        binding.webviewVideoPlayer.setOnFocusChangeListener { view, focused ->
            if (focused) {
                requestFocusAndShowKeyboard(binding.edittextLessonNotes)
            }
        }

        binding.edittextLessonNotes.setOnFocusChangeListener { view, focused ->

            if (!focused && !binding.webviewVideoPlayer.isFocused) {
                hideKeyboard(view)
                binding.webviewVideoPlayer.onPause()
                binding.webviewVideoPlayer.visibility = View.GONE
                binding.buttonLessonVideo.visibility = View.VISIBLE
            }
        }

        if (user.activeLesson != null) {
            binding.textviewLessonTopic.text =
                "${user.getLessonNumber(user.activeLesson!!)}. ${user.activeLesson!!.topic}"
            binding.textviewLessonLength.text =
                "${user.convertLessonLength(user.activeLesson!!.lengthInMin)}"
            binding.textviewLessonDescription.text = user.activeLesson!!.description
            binding.buttonLessonVideo.setOnClickListener {

                //remove button
                binding.buttonLessonVideo.visibility = View.GONE
                //play video
                if (startedVideo) {
                    binding.webviewVideoPlayer.visibility = View.VISIBLE
                    binding.webviewVideoPlayer.onResume()
                } else {
                    binding.webviewVideoPlayer.visibility = View.VISIBLE
                    binding.webviewVideoPlayer.webViewClient = WebViewClient()
                    binding.webviewVideoPlayer.settings.javaScriptEnabled = true
                    binding.webviewVideoPlayer.settings.javaScriptCanOpenWindowsAutomatically = true
                    binding.webviewVideoPlayer.settings.pluginState = WebSettings.PluginState.ON
                    binding.webviewVideoPlayer.settings.mediaPlaybackRequiresUserGesture = false
                    binding.webviewVideoPlayer.webChromeClient = WebChromeClient()
                    binding.webviewVideoPlayer.loadUrl(user.activeLesson!!.videoLink)
                    startedVideo = true
                }

            }
            binding.edittextLessonNotes.setText(user.activeLesson!!.notes)
            binding.buttonUpdateNotes.setOnClickListener {
                user.activeLesson!!.notes = binding.edittextLessonNotes.text.toString()

                with(sharedPrefs!!.edit()) {
                    putString(
                        user.lessons[user.lessons.indexOf(user.activeLesson)].icon!!.toString() + "notes",
                        user.activeLesson!!.notes
                    )
                    apply()
                    Snackbar.make(binding.root, "Notes Saved!", Snackbar.LENGTH_SHORT).show()
                }
            }
            binding.buttonLessonCompleted.setOnClickListener {
                user.activeLesson!!.isComplete = true
                user.lessons[user.lessons.indexOf(user.activeLesson)] = user.activeLesson!!

                with(sharedPrefs!!.edit()) {
                    putBoolean(
                        user.lessons[user.lessons.indexOf(user.activeLesson)].icon!!.toString() + "checked",
                        user.activeLesson!!.isComplete
                    )
                    apply()
                }
                user.activeLesson = null
                finish()
            }
        } else {
            finish()
        }
    }
}