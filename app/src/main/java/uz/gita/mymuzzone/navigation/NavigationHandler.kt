package uz.gita.mymuzzone.navigation

import kotlinx.coroutines.flow.SharedFlow
import uz.gita.mymuzzone.navigation.NavigationArg

interface NavigationHandler {
    val navigatorBuffer:SharedFlow<NavigationArg>
}