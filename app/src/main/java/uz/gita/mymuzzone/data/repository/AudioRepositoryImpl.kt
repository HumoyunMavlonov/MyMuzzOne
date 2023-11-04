package uz.gita.mymuzzone.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.gita.mymuzzone.data.local.ContentResolverHelper
import uz.gita.mymuzzone.data.local.model.AudioModel
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    val contentResolverHelper: ContentResolverHelper
) :AudioRepository{
    override suspend fun getAudioData(): List<AudioModel> = withContext(Dispatchers.IO) {
        contentResolverHelper.getAudioData()
    }
}