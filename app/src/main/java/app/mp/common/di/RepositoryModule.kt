package app.mp.common.di

import app.mp.model.repo.def.AudioRepository
import app.mp.model.repo.def.TokenRepository
import app.mp.model.repo.impl.AudioRepositoryImpl
import app.mp.model.repo.impl.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAudioRepository(repository: AudioRepositoryImpl): AudioRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(repository: TokenRepositoryImpl): TokenRepository

}