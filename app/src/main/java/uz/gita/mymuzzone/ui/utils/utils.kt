package uz.gita.mymuzzone.ui.utils

import android.util.Log


fun String.myLog(prefix:String = "myLog",tag:String = "TTT"){
    Log.d(tag, "$prefix :$this")
}