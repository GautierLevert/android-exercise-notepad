package fr.iut_amiens.notepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.iut_amiens.notepad.data.model.Note;

class NoteViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    private Note note;

    public NoteViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, EditActivity.class)
                        .putExtra(EditActivity.EXTRA_NOTE_ID, note.getId()));
            }
        });
    }

    public void bind(Note note) {
        this.note = note;
        textView.setText(note.getTitle());
    }
}
