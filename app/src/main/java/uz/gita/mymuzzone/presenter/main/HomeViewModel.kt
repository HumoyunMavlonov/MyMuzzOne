package uz.gita.mymuzzone.presenter.main

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import uz.gita.mymuzzone.data.repository.AudioRepository
import uz.gita.mymuzzone.player.service.AudioService
import uz.gita.mymuzzone.player.service.AudioServiceHandler
import uz.gita.mymuzzone.player.service.AudioState
import uz.gita.mymuzzone.player.service.PlayerEvent
import uz.gita.mymuzzone.ui.utils.myLog
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val audioServiceHandler: AudioServiceHandler,
    val audioRepository: AudioRepository,
    @ApplicationContext val  context: Context
) :HomeContract.HomeViewModel, ScreenModel{
    private var isServerRunning =false



    override val container=coroutineScope.container<HomeContract.UiState,HomeContract.SideEffect>(HomeContract.UiState())


    init {
        audioServiceHandler.audioState.onEach { mediaState->
            when(mediaState){
                AudioState.Initial ->{}
                is AudioState.Buffering -> calculateProgressValue(mediaState.progress)
                is AudioState.Playing -> intent { reduce { this.state.copy(isAudioPlaying = mediaState.isPlaying) } }
                is AudioState.Progress -> calculateProgressValue(mediaState.progress)
                is AudioState.CurrentPlaying -> intent { reduce { this.state.copy(currentAudioModel = container.stateFlow.value.audioList[mediaState.mediaItemIndex]) } }
                 is AudioState.Ready -> intent { reduce {
                     this.state.copy(duration = mediaState.duration)
                 } }

                else -> {}
            }
        }.launchIn(coroutineScope)
    }
    override fun onEventDispatchers(event: HomeContract.Event)  = intent{
        when(event){
            HomeContract.Event.loadingAudioData -> loadAudioData()
            HomeContract.Event.onPrev -> audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToPrev)
            HomeContract.Event.onNext -> audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToNext)
            HomeContract.Event.onStart -> audioServiceHandler.onPlayerEvents(PlayerEvent.PlayPause)

            is HomeContract.Event.onItemClick ->selectMusic(event.index)

            else -> {}
        }

    }
    private fun selectMusic(index: Int) {
        audioServiceHandler.onPlayerEvents(
            PlayerEvent.SelectAudioChange,
            selectedAudioIndex = index
        )
        startService()
    }
    private fun startService() {
        if (!isServerRunning) {
            val intent = Intent(context, AudioService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            isServerRunning = true
        }
    }

    override fun onDispose() {
        coroutineScope.launch {
            audioServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onDispose()
    }

    private fun loadAudioData() = intent {
        val audio = audioRepository.getAudioData()
        "loadAudioData:${audio.size}".myLog()
        reduce { this.state.copy(audioList = audio) }
        setMediaItems()
    }
    private fun setMediaItems() {
        container.stateFlow.value.audioList.map { audio ->
            MediaItem.Builder()
                .setUri(audio.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setSubtitle(audio.displayName)
                        .build()
                ).build()
        }.also { audioServiceHandler.setMediaItemList(it) }

    }

    private fun calculateProgressValue(currentProgress: Long) = intent {
        reduce {
            this.state.copy(
                progressString = formatDuration(currentProgress),
                progress = if (currentProgress > 0) ((currentProgress.toFloat() / container.stateFlow.value.duration.toFloat()) * 100f) else 0f
            )
        }
    }
}

private fun formatDuration(duration: Long): String {
    val minute = TimeUnit.MINUTES.convert(duration, TimeUnit.MICROSECONDS)
    val seconds = (minute) - minute * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)

    return String.format("%02d:%02d", minute, seconds)

}

