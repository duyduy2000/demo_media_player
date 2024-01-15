package app.mp.common.di

import app.mp.common.util.media.PlayerServiceBinder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppDependencies {
    fun serviceBinder(): PlayerServiceBinder
}