package com.example.codespace

import android.animation.ObjectAnimator
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.codespace.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityHomeBinding
    private var user: UserData = UserData.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTextValuesInView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnWelcomeContinue.setOnClickListener(this)
        binding.txvBtnDeleteAllData.setOnClickListener(this)
    }

    private fun setTextValuesInView() {
        val totalLessons = user.lessons.size
        val completedLessons = user.getCompletedLessonsCount()
        val username = user.name
        val percentageCompleted: Int = ((completedLessons.toDouble() / totalLessons) * 100).toInt()

        println(2 / 3)

        val welcomeBackTxt = "Welcome Back, $username"
        val completeStatus = "You have completed $percentageCompleted% of the course."
        binding.txvWelcomeBack.text = welcomeBackTxt
        binding.txvCompleteStatus.text = completeStatus
        binding.lessonsCompletedTxv.text = completedLessons.toString()
        binding.lessonsRemainingTxv.text = (totalLessons - completedLessons).toString()
        binding.progressPercentTxv.text = "$percentageCompleted%"
        loadPercentageProgressBar(percentageCompleted)
    }

    private fun loadPercentageProgressBar(percentage: Int) {
        Handler().postDelayed({
            ObjectAnimator.ofInt(binding.progressbar, "progress", percentage)
                .setDuration(800)
                .start();
        }, 800)
    }


    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.btn_welcome_continue -> onContinuePress()
                R.id.txv_btn_delete_all_data -> showDeleteAlertDialog()
            }
        }
    }

    private fun resetAllData() {
        val sharedPrefs: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        with(sharedPrefs.edit()) {
            clear()
            apply()
        }
        user.resetObject()
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }

    private fun showDeleteAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("DELETE")
        alertDialog.setMessage("Are you sure you want to delete all of your data?")
        alertDialog.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.cancel()
            })
        alertDialog.setPositiveButton(
            "DELETE",
            DialogInterface.OnClickListener { _, _ -> resetAllData() })
        alertDialog.show()
    }

    private fun onContinuePress() {
        startActivity(Intent(this, ViewLessonsActivity::class.java))
        finish()
    }

}