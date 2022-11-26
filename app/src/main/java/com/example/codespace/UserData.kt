package com.example.codespace

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.preference.PreferenceManager
import android.provider.Settings.Global.getString
import android.util.Log

class UserData {

    private constructor()

    companion object {
        @Volatile
        private lateinit var instance: UserData

        fun getInstance(): UserData {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = UserData()
                }
                return instance
            }
        }
    }

    var name: String? = null
    var lessons = arrayListOf<Lesson>(
        Lesson(
            R.drawable.one,
            "Install and Setup for Mac and Windows",
            16,
            "We will start with the basics of how to install and setup Python for Mac and Windows. We will also take a look at the interactive prompt, as well as creating and running our first script. Let's get started.",
            "https://www.youtube.com/watch?v=YYXdXT2l-Gg&list=PLNlXElf3dXzMOgHUs93ZFy4aBcLE7RjnP"
        ),
        Lesson(
            R.drawable.two,
            "Strings - Working with Textual Data",
            21,
            "Strings allow us to work with textual data in Python. We will be going over different ways to format strings, and also a lot of useful string methods. Let's get started.",
            "https://www.youtube.com/watch?v=k9TUPpGqYTo&list=PLNlXElf3dXzMOgHUs93ZFy4aBcLE7RjnP"
        ),
        Lesson(
            R.drawable.three,
            "Integers and Floats - Working with Numeric Data",
            12,
            "Integers and Floats allow us to work with numeric data in Python. We will be learning how to perform basic arithmetic, as well as how to compare numeric values. Let's get started.",
            "https://www.youtube.com/watch?v=khKv-8q7YmY&list=PLNlXElf3dXzMOgHUs93ZFy4aBcLE7RjnP"
        ),
        Lesson(
            R.drawable.four,
            "Lists, Tuples, and Sets",
            29,
            "Lists and Tuples allow us to work with sequential data, and Sets allow us to work with unordered unique values. We will go over most of the methods, learn when to use which data type, and also the performance benefits of each type as well. Let's get started.",
            "https://www.youtube.com/watch?v=W8KRzm-HUcc&list=PLNlXElf3dXzMOgHUs93ZFy4aBcLE7RjnP"
        ),
        Lesson(
            R.drawable.five,
            "Dictionaries - Working with Key-Value Pairs",
            10,
            "Dictionaries allow us to work with key-value pairs in Python. We will go over dictionary methods, how to add and remove values, and also how to loop through the key-value pairs. Let's get started.",
            "https://www.youtube.com/watch?v=daefaLgNkw0&list=PLNlXElf3dXzMOgHUs93ZFy4aBcLE7RjnP"
        )
    )
    var sequentialProgress = false
    var activeLesson: Lesson? = null

    fun updateUserDataFromPrefs(preferences: SharedPreferences) {

        name = preferences.getString(
            "username",
            name
        )
        sequentialProgress = preferences.getBoolean("FORCED_PROGRESSION", sequentialProgress)

        for (lesson in lessons) {
            lesson.notes = preferences.getString(lesson.icon.toString() + "notes", lesson.notes)!!
            lesson.isComplete =
                preferences.getBoolean(lesson.icon.toString() + "checked", lesson.isComplete)
        }
    }

    fun getLessonNumber(lesson: Lesson): Int {
        return lessons.indexOf(lesson) + 1
    }

    fun getPreviousLessonIndex(currentLesson: Lesson): Int {
        return lessons.indexOf(currentLesson) - 1
    }

    fun getCompletedLessonsCount(): Int {
        var completedLessons: Int = 0
        for (lesson in lessons) {
            if (lesson.isComplete) {
                completedLessons++
            }
        }
        return completedLessons
    }

    fun convertLessonLength(minutes: Int): String {

        var newTime: String = "Length: "

        newTime += if (minutes > 60) {
            val minRemainder = minutes % 60
            val hours = minutes / 60
            "$hours hr $minRemainder min"
        } else {
            "$minutes min"
        }

        return newTime
    }

    fun createPrefs(preferences: SharedPreferences, newName: String) {

        with(preferences.edit()) {
            Log.d(preferences.toString(), "Creating data")
            putString("username", newName)
            putBoolean("FORCED_PROGRESSION", sequentialProgress)

            for (lesson in lessons) {
                putString(lesson.toString() + "notes", lesson.notes)
                putBoolean(lesson.toString() + "checked", lesson.isComplete)
            }
            apply()
        }
    }

    fun resetObject() {
        activeLesson = null
        sequentialProgress = false
        name = null
        for (lesson in lessons) {
            lesson.isComplete = false
            lesson.notes = ""
        }
    }
}
