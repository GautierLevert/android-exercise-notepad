package fr.iut_amiens.notepad.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gautier on 04/02/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "database";

    private static final int VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS note (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
