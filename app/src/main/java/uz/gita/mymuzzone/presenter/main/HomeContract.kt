package uz.gita.mymuzzone.presenter.main

import org.orbitmvi.orbit.ContainerHost
import uz.gita.mymuzzone.data.local.model.AudioModel
import uz.gita.mymuzzone.data.local.model.defaultAudioModel

interface HomeContract {
    interface HomeViewModel:ContainerHost<UiState,SideEffect>{
        fun onEventDispatchers(event: Event)

    }

    data class UiState(
        val duration : Long = 0L,
        val progress:Float = 0f,
        val progressString : String = "00:00",
        val isAudioPlaying:Boolean = false,
        val audioList:List<AudioModel> = emptyList(),
        val currentAudioModel :AudioModel = defaultAudioModel
    )

    sealed interface SideEffect{
        object Init :SideEffect
    }
    sealed interface Event{
        object Init :Event
        object loadingAudioData:Event
        object onStart:Event
        object onNext:Event
        object onPrev:Event
        data class onItemClick(val index:Int):Event

    }
}