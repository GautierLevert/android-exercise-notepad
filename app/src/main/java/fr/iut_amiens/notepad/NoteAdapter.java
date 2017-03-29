package fr.iut_amiens.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private List<Note> notes = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public NoteAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void add(Note title) {
        notes.add(title);
        notifyDataSetChanged();
    }

    public void clear() {
        notes.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = layoutInflater.inflate(R.layout.listitem_title, parent, false);
        } else {
            v = convertView;
        }
        TextView textView = (TextView) v.findViewById(R.id.textView);
        textView.setText(getItem(position).getTitle());
        return v;
    }
}
