package fr.iut_amiens.notepad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class EditActivity extends Activity {

    public static final String EXTRA_NOTE_TITLE = "fr.iut_amiens.notepad.EXTRA_NOTE_TITLE";

    private String noteTitle;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        noteTitle = getIntent().getStringExtra(EXTRA_NOTE_TITLE);
        editText = (EditText) findViewById(R.id.editText);

        setTitle(noteTitle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText.append(getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE).getString(noteTitle, ""));
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE).edit().putString(noteTitle, editText.getText().toString()).apply();
    }
}
