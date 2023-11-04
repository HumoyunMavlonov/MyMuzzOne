package uz.gita.mymuzzone.ui.utils

import kotlin.math.floor


fun timeStampToDuration(position: Long): String {
    val totalSecond = floor(position / 1E3).toInt()
    val minutes = totalSecond / 60
    val remainingSeconds = totalSecond - (minutes * 60)

    return if (position < 0) "--:--"
    else "%d:%02d".format(minutes, remainingSeconds)
}