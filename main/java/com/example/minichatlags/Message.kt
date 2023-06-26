package com.example.minichatlags

import java.util.*

data class Message(
    var message: String = "",
    var from: String="",
    var dob: Date = Date()
)