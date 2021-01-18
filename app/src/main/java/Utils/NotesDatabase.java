package Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Models.NotesEntity;

@Database(entities = { NotesEntity.class }, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();

    private static final String dbName= "NotesDB";
    private static NotesDatabase noteDB;

    public static NotesDatabase getInstance(Context context) {
        if (null == noteDB) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static NotesDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                NotesDatabase.class,
                dbName)
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        noteDB = null;
    }

}
