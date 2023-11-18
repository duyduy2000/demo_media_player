package app.mp.common.di

import app.mp.model.repo.def.TrackRepository
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
    abstract fun bindNoteRepository(noteRepositoryImpl: TrackRepositoryImpl): TrackRepository

}