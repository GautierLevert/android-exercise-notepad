package fr.iut_amiens.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import fr.iut_amiens.notepad.data.DatabaseOpenHelper;
import fr.iut_amiens.notepad.data.model.Note;

public class MainActivity extends Activity {

    private NoteAdapter adapter;

    private DatabaseOpenHelper dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new NoteAdapter(getLayoutInflater());

        RecyclerView recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        dao = new DatabaseOpenHelper(this);
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
            builder.setView(getLayoutInflater().inflate(R.layout.dialog_add_note, null, false));
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText editText = ((AlertDialog) dialog).findViewById(R.id.editText);
                    newNote(editText.getText().toString());
                }
            });
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newNote(String title) {
        Log.d("NOTE", "create note with title: " + title);
        dao.getWritableDatabase().execSQL("INSERT INTO note (title, content) VALUES (?, \"\")", new String[]{title});
        refreshList();
    }

    private void refreshList() {
        adapter.clear();
        Cursor cursor = dao.getReadableDatabase().query("note", new String[]{"_id", "title", "content"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            adapter.add(new Note(cursor.getLong(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("content"))));
        }
        cursor.close();
    }
}
