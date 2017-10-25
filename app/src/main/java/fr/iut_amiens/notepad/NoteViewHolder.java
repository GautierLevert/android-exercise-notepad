package fr.iut_amiens.notepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class NoteViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    private String noteTitle;

    public NoteViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, EditActivity.class)
                        .putExtra(EditActivity.EXTRA_NOTE_TITLE, noteTitle));
            }
        });
    }

    public void bind(String title) {
        noteTitle = title;
        textView.setText(title);
    }
}
