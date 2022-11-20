package com.example.codespace

class UserData {

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

    var name:String? = null
    var lessons = arrayListOf<Lesson>(
        Lesson(R.drawable.one, "Topic 1", 10, "this is the content for topic 1", "https://www.youtube.com/watch?v=HIj8wU_rGIU"),
        Lesson(R.drawable.two,"Topic 2", 20, "this is the content for topic 2", "https://www.youtube.com/watch?v=HIj8wU_rGIU"),
        Lesson(R.drawable.three,"Topic 3", 30, "this is the content for topic 3", "https://www.youtube.com/watch?v=HIj8wU_rGIU"),
        Lesson(R.drawable.four,"Topic 4", 80, "this is the content for topic 4", "https://www.youtube.com/watch?v=HIj8wU_rGIU")
    )
    var sequentialProgress = false
    var activeLesson:Lesson? = null

    fun getLessonNumber(lesson:Lesson):Int{
        return lessons.indexOf(lesson) + 1
    }

    fun getPreviousLessonIndex(currentLesson:Lesson):Int{
        return lessons.indexOf(currentLesson) - 1
    }

    fun convertLessonLength(minutes:Int):String{

        var newTime:String = "Length: "

        newTime += if (minutes > 60){
            val minRemainder = minutes % 60
            val hours = minutes / 60
            "$hours hr $minRemainder min"
        } else{
            "$minutes min"
        }

        return newTime
    }
}
