package uz.gita.bookplayer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

/**
 * Created by Shukrullo Zokirov on 11/3/2023.
 */
@Composable
fun WrapperColumn(content:@Composable ColumnScope.()->Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}