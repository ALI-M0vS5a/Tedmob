package dev.alimoussa.tedmob.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.alimoussa.tedmob.network.TedmobNetworkDataSource
import dev.alimoussa.tedmob.network.retrofit.RetrofitNiaNetwork

@Module
@InstallIn(SingletonComponent::class)
internal interface Module {

    @Binds
    fun binds(impl: RetrofitNiaNetwork): TedmobNetworkDataSource
}