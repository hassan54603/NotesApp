package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {
    Context context;
    List<Notes> notesList;
    public NotesAdapter(Context context, List<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View notLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_noteslist,parent,false);
        return new NotesHolder(notLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {

        Notes notes = notesList.get(position);
        holder.title.setText(notes.getTitle());
        holder.content.setText(notes.getContent());
        holder.notedate.setText(notes.getNotedate());

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,EditActivity.class);
            intent.putExtra("title",notes.title);
            intent.putExtra("content",notes.content);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class NotesHolder extends RecyclerView.ViewHolder {
        TextView title,content,notedate;
        public NotesHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            content =  itemView.findViewById(R.id.note_content);
            notedate =  itemView.findViewById(R.id.note_date);
        }
    }
}
