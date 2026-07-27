package com.example.journalapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllJournals extends AppCompatActivity {

    RecyclerView recyclerView;
    JournalAdapter adapter;
    List<Journal> journalList;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_journal);

        recyclerView = findViewById(R.id.journalRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        journalList = new ArrayList<>();
        adapter = new JournalAdapter(journalList, this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loadJournals();
    }

    private void loadJournals() {
        String uid = auth.getCurrentUser().getUid();
        db.collection("users").document(uid).collection("journals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    journalList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Journal journal = document.toObject(Journal.class);
                        journal.setId(document.getId());
                        journalList.add(journal);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void deleteJournal(Journal journal) {
        String uid = auth.getCurrentUser().getUid();
        db.collection("users").document(uid).collection("journals").document(journal.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    journalList.remove(journal);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Journal deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
