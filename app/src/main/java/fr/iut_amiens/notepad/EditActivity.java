package fr.iut_amiens.notepad;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import fr.iut_amiens.notepad.data.DatabaseOpenHelper;

public class EditActivity extends Activity {

    public static final String EXTRA_NOTE_ID = "fr.iut_amiens.notepad.EXTRA_NOTE_ID";

    private long noteId;

    private EditText editText;

    private DatabaseOpenHelper dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        noteId = getIntent().getLongExtra(EXTRA_NOTE_ID, -1);
        editText = findViewById(R.id.editText);

        dao = new DatabaseOpenHelper(this);

        Cursor c = dao.getReadableDatabase().rawQuery("SELECT title, content FROM note WHERE _id = ?", new String[]{Long.toString(noteId)});
        if (c.moveToFirst()) {

            String title = c.getString(c.getColumnIndex("title"));
            String content = c.getString(c.getColumnIndex("content"));

            Log.d("NOTE", "read content: \"" + content + "\" in note " + noteId);

            setTitle(title);
            editText.append(content);
        } else {
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        String content = editText.getText().toString();
        Log.d("NOTE", "save content: \"" + content + "\" in note " + noteId);
        dao.getWritableDatabase().execSQL("UPDATE note SET content = ? WHERE _id = ?", new String[]{content, Long.toString(noteId)});
    }
}
