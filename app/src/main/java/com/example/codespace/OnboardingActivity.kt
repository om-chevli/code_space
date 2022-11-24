package com.example.codespace

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.codespace.databinding.ActivityOnboardingBinding


class OnboardingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOnboardingBinding
    private val user:UserData = UserData.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFCLForEdtName()
        binding.btnObContinue.setOnClickListener(this)
    }

    private fun setFCLForEdtName() {
        binding.edtObName.setOnFocusChangeListener { view, b ->
            hideKeyboard(view)
        }
    }


    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.btn_ob_continue -> onContinueButtonClick()
            }
        }
    }

    private fun onContinueButtonClick() {
        val name: String = binding.edtObName.text.toString().trim()
        println(name)
        if (name.length < 3) {
            binding.edtObName.error = "Name should be at least 3 characters long! "
            binding.edtObName.requestFocus()
        } else {
            val sharedPrefs: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key),
                MODE_PRIVATE
            )
            user.createPrefs(sharedPrefs, name)
            with(sharedPrefs.edit()) {
                this.putString(getString(R.string.prefs_username_key), name)
                apply()
            }

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}