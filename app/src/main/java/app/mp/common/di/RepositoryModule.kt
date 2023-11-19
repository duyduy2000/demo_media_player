package app.mp.common.di

import app.mp.model.repo.def.TokenRepository
import app.mp.model.repo.def.TrackRepository
import app.mp.model.repo.impl.TokenRepositoryImpl
import app.mp.model.repo.impl.TrackRepositoryImpl
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
    abstract fun bindTrackRepository(repository: TrackRepositoryImpl): TrackRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(repository: TokenRepositoryImpl): TokenRepository

}