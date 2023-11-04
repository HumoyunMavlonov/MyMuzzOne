package uz.gita.mymuzzone.presenter.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getScreenModel
import uz.gita.bookplayer.ui.components.PlayerIconItem
import uz.gita.bookplayer.ui.components.WrapperColumn
import uz.gita.mymuzzone.R
import uz.gita.mymuzzone.navigation.MyScreen
import uz.gita.mymuzzone.presenter.components.MusicItem
import uz.gita.mymuzzone.ui.utils.SetUiController
import uz.gita.mymuzzone.ui.utils.myLog

class HomeScreen : MyScreen() {
    companion object {
        private val mediaPlayer = MediaPlayer()
    }


    @Composable
    override fun Content() {
        val vm: HomeContract.HomeViewModel = getScreenModel<HomeViewModel>()
        vm.onEventDispatchers(HomeContract.Event.loadingAudioData)
        MainContent(


            uiState = vm.container.stateFlow.collectAsState(),
            onEventDispatcher = vm::onEventDispatchers
        )

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainContent(
        uiState: State<HomeContract.UiState>,
        onEventDispatcher: (HomeContract.Event) -> Unit
    ) {
        SetUiController(
            statusBarColor = White,
            isDarkIconEnable = true
        )
        Box(contentAlignment = Alignment.BottomCenter) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                "lazy->${uiState.value.audioList.size}".myLog()
                item { Text(text = "Music List", modifier = Modifier.padding(16.dp)) }
                itemsIndexed(uiState.value.audioList) { index, audio ->
                    MusicItem(music = audio) {
                        onEventDispatcher(HomeContract.Event.onItemClick(index))
                    }

                }
            }

            if (uiState.value.currentAudioModel.title.isNotEmpty()) {
                "currentAudio uri:${uiState.value.currentAudioModel.uri}".myLog()
                Card(
                    shape = CircleShape.copy(all = CornerSize(32.dp)),
                ) {
                    WrapperColumn {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp)
                                .height(2.dp),
                            progress = uiState.value.progress / 100f,
                        )
                        Row(
                            Modifier.padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(4.dp)
                                    .clip(CircleShape),
                                bitmap = getAlbumArt(
                                    LocalContext.current,
                                    uiState.value.currentAudioModel.uri
                                ).asImageBitmap(), contentDescription = "Audio image"
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = uiState.value.currentAudioModel.title,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleLarge,
                                    overflow = TextOverflow.Clip,
                                    maxLines = 1,
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(
                                    text = uiState.value.currentAudioModel.artist,
                                    fontWeight = FontWeight.Normal,
                                    style = MaterialTheme.typography.titleSmall,
                                    overflow = TextOverflow.Clip,
                                    maxLines = 1
                                )
                            }
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(imageVector = Icons.Default.SkipPrevious,
                                contentDescription = null,
                                modifier = Modifier.clickable { onEventDispatcher(HomeContract.Event.onPrev) })
                            Spacer(modifier = Modifier.size(8.dp))
                            PlayerIconItem(icon = if (uiState.value.isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow) {
                                onEventDispatcher(HomeContract.Event.onStart)
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(imageVector = Icons.Default.SkipNext, contentDescription = null,
                                modifier = Modifier.clickable { onEventDispatcher(HomeContract.Event.onNext) })
                        }
                    }
                }
            }


        }
    }
}

fun getAlbumArt(context: Context, uri: Uri): Bitmap {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(context, uri)
    val data = mmr.embeddedPicture
    return if (data != null) {
        BitmapFactory.decodeByteArray(data, 0, data.size)
    } else {
        BitmapFactory.decodeResource(context.resources, R.drawable.musical_note)
    }
}