package uz.gita.mymuzzone.data.local

import uz.gita.mymuzzone.data.local.model.AudioModel

interface ContentResolverHelper {
    suspend fun getAudioData():List<AudioModel>
}