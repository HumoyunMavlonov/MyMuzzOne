package uz.gita.mymuzzone.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Created by Shukrullo Zokirov on 10/27/2023.
 */
@Composable
fun SetUiController(
    statusBarColor:Color = MaterialTheme.colorScheme.primary,
    navigationBarColor:Color = Color.Black,
    isDarkIconEnable:Boolean = false,
    isStatusBarVisible:Boolean = true,
    isNavigationBarVisible:Boolean = true,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(statusBarColor)
    systemUiController.statusBarDarkContentEnabled = isDarkIconEnable
    systemUiController.setNavigationBarColor(navigationBarColor)
    systemUiController.isStatusBarVisible = isStatusBarVisible
    systemUiController.isNavigationBarVisible = isNavigationBarVisible

}