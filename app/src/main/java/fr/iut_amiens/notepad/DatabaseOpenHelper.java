package fr.iut_amiens.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

    private static final String LOG_TAG = DatabaseOpenHelper.class.getSimpleName();

    private static final String DATABASE = "database";

    private static final int VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Note.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Note, Long> getNoteDao() throws SQLException {
        return DaoManager.createDao(getConnectionSource(), Note.class);
    }
}
