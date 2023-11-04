package uz.gita.mymuzzone.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import uz.gita.mymuzzone.presenter.main.HomeContract
import uz.gita.mymuzzone.presenter.main.HomeViewModel


@Module
@InstallIn(SingletonComponent::class)
interface HiltModule {

    @[Binds IntoMap ScreenModelKey(HomeViewModel::class)]
    fun bindHomeVM(impl:HomeViewModel):ScreenModel
}