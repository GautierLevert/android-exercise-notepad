package fr.iut_amiens.notepad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import fr.iut_amiens.notepad.data.DatabaseOpenHelper;
import fr.iut_amiens.notepad.data.model.Note;

public class EditActivity extends Activity {

    private static final String LOG_TAG = EditActivity.class.getSimpleName();

    public static final String EXTRA_NOTE_ID = "fr.iut_amiens.notepad.EXTRA_NOTE_ID";

    private Note note;

    private EditText editText;

    private Dao<Note, Long> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = findViewById(R.id.editText);

        try {
            dao = OpenHelperManager.getHelper(this, DatabaseOpenHelper.class).getNoteDao();
            note = dao.queryForId(getIntent().getLongExtra(EXTRA_NOTE_ID, -1));

            if (note != null) {
                Log.d("NOTE", "read content: \"" + note);
                setTitle(note.getTitle());
                editText.append(note.getContent());
            } else {
                finish();
            }
        } catch (SQLException e) {
            Log.e(LOG_TAG, "", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenHelperManager.releaseHelper();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (note != null) {
            note.setContent(editText.getText().toString());
            Log.d("NOTE", "save content: \"" + note);

            if (dao != null) {
                try {
                    dao.update(note);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
