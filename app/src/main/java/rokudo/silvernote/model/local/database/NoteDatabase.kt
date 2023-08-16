package rokudo.silvernote.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import rokudo.silvernote.model.local.dao.NoteDao
import rokudo.silvernote.model.local.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class Callback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { database ->
                scope.launch { populateData(dao = database.noteDao()) }
            }
        }

        fun populateData(dao: NoteDao) = dao.insert(Note(id = 0, title = "Welcome!"))
    }

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) = instance ?: synchronized(this) {
            instance = Room.databaseBuilder(
                context = context.applicationContext,
                klass = NoteDatabase::class.java,
                name = "note_db"
            ).addCallback(Callback(scope = scope)).build()
            return instance!!
        }
    }
}