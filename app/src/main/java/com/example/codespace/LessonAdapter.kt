package com.example.codespace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.codespace.databinding.LessonTempateBinding

class LessonAdapter(context: Context, dataSource: ArrayList<Lesson>) : ArrayAdapter<Lesson>(context, 0, dataSource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val user:UserData = UserData.getInstance()
        val currentLesson = getItem(position)
        var lessonListBinding = LessonTempateBinding.inflate(LayoutInflater.from(context), parent, false)
        var lessonView = lessonListBinding.root

        if (currentLesson != null){

            lessonListBinding.imageviewLessonNumber.setImageResource(currentLesson.icon)
            lessonListBinding.textviewLessonTopic.text = currentLesson.topic
            lessonListBinding.textviewLessonLength.text = user.convertLessonLength(currentLesson.lengthInMin)
            if (currentLesson.isComplete){
                lessonListBinding.imageviewLessonComplete.visibility = View.VISIBLE
            }

        }
        return lessonView
    }
}