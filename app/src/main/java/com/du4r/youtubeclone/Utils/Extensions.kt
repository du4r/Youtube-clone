package com.du4r.youtubeclone

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatTime(): String{
    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60
    return String.format("%02d : %02d", minutes,seconds)
}

fun String.toDate(): Date? =
    SimpleDateFormat("yyyy-mm-dd", Locale("pt","BR")).parse(this)

fun Date.Formatted(): String =
    SimpleDateFormat("d MMM yyyy", Locale("pt","BR")).format(this)