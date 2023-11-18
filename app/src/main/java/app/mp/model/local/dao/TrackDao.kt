package app.mp.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mp.model.local.entity.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Query("select * from track_tbl")
    fun getAllTracks(): Flow<List<Track>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: Track)

    @Delete
    fun delete(trackList: List<Track>)
}