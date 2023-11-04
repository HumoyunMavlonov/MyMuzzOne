package uz.gita.mymuzzone.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import uz.gita.mymuzzone.R
import uz.gita.mymuzzone.data.local.model.AudioModel


@Composable
fun MusicItem(
    music: AudioModel,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(68.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(horizontal = 17.dp)
    ) {
        Card(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterVertically),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(Color(0xFFF7F7F7))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    painterResource(id = R.drawable.music_icon),
                    modifier = Modifier
                        .align(Alignment.Center),
//                    imageVector = ImageVector.vectorResource(id = R.drawable),
                    contentDescription = "Place holder",
//                    tint = Color.White
                )

                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    painter = rememberAsyncImagePainter(
                        music.uri
                    ),
                    contentDescription = "Art image",
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(
            modifier = Modifier
                .width(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 15.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text =if (music.title.length < 25) music.title else "${music.title.substring(0, 25)}...",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.sfprotext_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                )
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Text(
                text =
                if (music.artist == "<unknown>") {
                    "Unknown"
                } else {
                    if (music.artist.length < 25) music.artist else "${music.artist.substring(0, 25)}..."
                }
               ,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.sfprotext_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xE4000000),
                )
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

            Image(
                painterResource(id = R.drawable.headphones),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterVertically),
//                iterations = LottieConstants.IterateForever
            )

        }
    }
