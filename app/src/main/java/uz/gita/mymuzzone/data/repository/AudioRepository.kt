package uz.gita.mymuzzone.data.repository

import uz.gita.mymuzzone.data.local.model.AudioModel

interface AudioRepository {
    suspend fun getAudioData():List<AudioModel>
}