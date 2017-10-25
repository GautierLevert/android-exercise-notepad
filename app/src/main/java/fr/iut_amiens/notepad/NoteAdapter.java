package fr.iut_amiens.notepad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<String> notes = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public NoteAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void add(String title) {
        notes.add(title);
        notifyDataSetChanged();
    }

    public void clear() {
        notes.clear();
        notifyDataSetChanged();
    }

    public String getItem(int position) {
        return notes.get(position);
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
