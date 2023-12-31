package app.mp.common.di

import app.mp.common.util.media.AudioPlayerState
import app.mp.common.util.media.PlayerServiceBinder
import app.mp.model.remote.api.FreesoundApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideNoteDatabase(@ApplicationContext context: Context) = lazy {
//        TrackDatabase.getDatabase(context = context, scope = CoroutineScope(SupervisorJob()))
//    }.value

    @Provides
    @Singleton
    fun provideFreesoundApi(): FreesoundApi = Retrofit.Builder()
        .baseUrl("https://freesound.org/apiv2/")
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(FreesoundApi::class.java)

    @Provides
    @Singleton
    fun provideAudioPlayerState() = AudioPlayerState()

    @Provides
    @Singleton
    fun provideAudioServiceBinder() = PlayerServiceBinder()
}