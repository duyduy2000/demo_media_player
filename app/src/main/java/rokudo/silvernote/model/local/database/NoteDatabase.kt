package rokudo.silvernote.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rokudo.silvernote.model.local.dao.NoteDao
import rokudo.silvernote.model.local.entities.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(this) {
            instance = Room.databaseBuilder(
                context = context.applicationContext,
                klass = NoteDatabase::class.java,
                name = "note_db"
            ).build()
            return instance!!
        }
    }
}