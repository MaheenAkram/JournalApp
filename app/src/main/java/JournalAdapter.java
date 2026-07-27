package com.example.journalapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    List<Journal> journals;
    AllJournals activity;

    public JournalAdapter(List<Journal> journals, AllJournals activity) {
        this.journals = journals;
        this.activity = activity;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_all_journals, parent, false);

        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {

        Journal journal = journals.get(position);

        holder.itemTitle.setText(journal.getTitle());
        holder.itemDescription.setText(journal.getDescription());


        holder.itemTimestamp.setText(String.valueOf(journal.getTimestamp()));


        holder.deleteBtn.setOnClickListener(v -> {
            activity.deleteJournal(journal);
        });
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }

    public static class JournalViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle, itemDescription, itemTimestamp;
        Button deleteBtn;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemTimestamp = itemView.findViewById(R.id.itemTimestamp);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}