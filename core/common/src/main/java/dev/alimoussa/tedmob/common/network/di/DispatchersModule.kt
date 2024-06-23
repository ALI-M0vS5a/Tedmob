package dev.alimoussa.tedmob.common.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.alimoussa.tedmob.common.network.Dispatcher
import dev.alimoussa.tedmob.common.network.TedmobDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(TedmobDispatchers.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(TedmobDispatchers.MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(TedmobDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}