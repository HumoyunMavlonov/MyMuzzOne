package uz.gita.mymuzzone.navigation


interface AppNavigator{
    suspend fun navigateTo(screen : MyScreen)

    suspend fun back()
}