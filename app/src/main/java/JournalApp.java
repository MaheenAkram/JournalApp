package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JournalApp extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;

    EditText etJournalTitle, etJournalContent;
    Button saveEntry, allJournals, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_journal_app);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }

        etJournalTitle = findViewById(R.id.tittle);
        etJournalContent = findViewById(R.id.discription);
        saveEntry = findViewById(R.id.saveEntry);
        allJournals = findViewById(R.id.allJournals);
        logout = findViewById(R.id.logout);


        saveEntry.setOnClickListener(v -> saveJournal());


        allJournals.setOnClickListener(v ->

                startActivity(new Intent(JournalApp.this, AllJournals.class))
        );

        logout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(JournalApp.this, Login.class));
            finish();
        });
    }

    private void saveJournal() {

        String title = etJournalTitle.getText().toString().trim();
        String content = etJournalContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = auth.getCurrentUser().getUid();

        Map<String, Object> journal = new HashMap<>();
        journal.put("title", title);
        journal.put("description", content);
        journal.put("timestamp", System.currentTimeMillis());

        db.collection("users")
                .document(uid)
                .collection("journals")
                .add(journal)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Journal saved!", Toast.LENGTH_SHORT).show();
                    etJournalTitle.setText("");
                    etJournalContent.setText("");
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}