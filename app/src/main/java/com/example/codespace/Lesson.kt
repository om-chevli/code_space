package com.example.codespace

class Lesson(
    val icon: Int,
    val topic: String,
    val lengthInMin: Int,
    val description: String,
    val videoLink: String,
    var isComplete: Boolean = false,
    var notes: String = ""
)