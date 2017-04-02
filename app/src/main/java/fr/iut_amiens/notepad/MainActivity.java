package fr.iut_amiens.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private NoteAdapter adapter;

    private Dao<Note, Long> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new NoteAdapter(getLayoutInflater());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        try {
            dao = OpenHelperManager.getHelper(this, DatabaseOpenHelper.class).getNoteDao();
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
    protected void onStart() {
        super.onStart();
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add a note");
            final EditText editText = new EditText(this);
            editText.setHint(R.string.note_title);
            editText.setSingleLine();
            builder.setView(editText);
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newNote(editText.getText().toString());
                }
            });
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newNote(String title) {
        if (dao != null) {
            try {
                Log.d("NOTE", "create note with title: " + title);
                dao.create(new Note(0, title, ""));
            } catch (SQLException e) {
                Log.e(LOG_TAG, "", e);
            }
        }
        refreshList();
    }

    private void refreshList() {
        adapter.clear();
        if (dao != null) {
            try {
                adapter.addAll(dao.queryForAll());
            } catch (SQLException e) {
                Log.e(LOG_TAG, "", e);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("NOTE", "click on list: " + id);
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EditActivity.EXTRA_NOTE_TITLE, id);
        startActivity(intent);
    }
}
