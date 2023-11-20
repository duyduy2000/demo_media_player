package app.mp.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.mp.model.local.dao.TrackDao
import app.mp.model.local.entity.TrackEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TrackEntity::class], version = 1, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

    private class Callback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { database ->
                scope.launch { populateData(dao = database.trackDao()) }
            }
        }

        fun populateData(dao: TrackDao) = dao.insert(TrackEntity(id = 0, title = "Welcome!"))
    }

    companion object {
        @Volatile
        private var instance: TrackDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) = instance ?: synchronized(this) {
            instance = Room.databaseBuilder(
                context = context.applicationContext,
                klass = TrackDatabase::class.java,
                name = "track_db"
            ).addCallback(Callback(scope = scope)).build()
            return instance!!
        }
    }
}