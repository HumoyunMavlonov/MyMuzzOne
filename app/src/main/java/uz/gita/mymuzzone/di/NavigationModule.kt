package uz.gita.mymuzzone.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mymuzzone.navigation.AppNavigator
import uz.gita.mymuzzone.navigation.NavigationDispatcher
import uz.gita.mymuzzone.navigation.NavigationHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModulee {
    
    @[Binds Singleton]
    fun bindAppNavigator(impl : NavigationDispatcher) : AppNavigator

    @[Binds Singleton]
    fun bindNavigationHandler(impl : NavigationDispatcher) : NavigationHandler
    
}