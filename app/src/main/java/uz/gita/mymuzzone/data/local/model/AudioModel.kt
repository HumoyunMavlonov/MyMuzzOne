package uz.gita.mymuzzone.data.local.model

import android.net.Uri
import androidx.core.net.toUri

data class AudioModel(
    val uri: Uri,
    val displayName:String,
    val id:Long,
    val artist:String,
    val data:String,
    val duration:Int,
    val title:String

    )

val defaultAudioModel = AudioModel(
    "".toUri(),"",0L,"","",0,""
)