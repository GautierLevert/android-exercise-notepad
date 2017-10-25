package fr.iut_amiens.notepad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.iut_amiens.notepad.data.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

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

    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(layoutInflater.inflate(R.layout.listitem_title, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
