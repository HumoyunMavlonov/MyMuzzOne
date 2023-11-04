package uz.gita.mymuzzone.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mymuzzone.data.local.ContentResolveHelperImpl
import uz.gita.mymuzzone.data.local.ContentResolverHelper
import uz.gita.mymuzzone.data.repository.AudioRepository
import uz.gita.mymuzzone.data.repository.AudioRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindAudioRepository(impl:AudioRepositoryImpl): AudioRepository

    @[Binds Singleton]
    fun bindContentResolverHelper(imp:ContentResolveHelperImpl): ContentResolverHelper
}