package com.example.codespace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.codespace.databinding.ActivityViewLessonsBinding
import com.google.android.material.snackbar.Snackbar

class ViewLessonsActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewLessonsBinding
    lateinit var lessonsAdapter: LessonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewLessonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val user:UserData = UserData.getInstance()

        binding.switchSeqProgress.setOnClickListener {
            user.sequentialProgress = binding.switchSeqProgress.isChecked
        }

        lessonsAdapter = LessonAdapter(this, user.lessons)
        binding.listviewLessons.adapter = lessonsAdapter

        //add visual "gray out" to indicate which lessons are unavailable when switch is on

        binding.listviewLessons.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val clickedLesson = user.lessons[position]

            if (!user.sequentialProgress || user.getLessonNumber(clickedLesson) == 1 || user.lessons[user.getPreviousLessonIndex(clickedLesson)].isComplete){
                user.activeLesson = clickedLesson

                intent = Intent(this@ViewLessonsActivity, DoLessonActivity::class.java)
                startActivity(intent)
            }
            else{
                Snackbar.make(binding.root, "You must complete the previous lesson to start this lesson", Snackbar.LENGTH_SHORT).show()
            }


        }
    }
}