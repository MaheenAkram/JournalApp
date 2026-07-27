package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

     EditText nameEt, emailEt, passEt;
     Button signupBtn;
     FirebaseAuth auth;
    TextView login2;
     FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameEt = findViewById(R.id.editTextText12);
        emailEt = findViewById(R.id.editTextText6);
        passEt = findViewById(R.id.editTextText11);
        signupBtn = findViewById(R.id.button);
        login2 = findViewById(R.id.login2);

        login2.setText(
                android.text.Html.fromHtml(
                        "Don't have an account? <font color='#40D0FF'>Log In</font>",
                        android.text.Html.FROM_HTML_MODE_LEGACY
                )
        );

        signupBtn.setOnClickListener(v -> {
            Log.i("click","Signup clicked");
            signupUser();
        });


        login2.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, Login.class);
            startActivity(intent);
        });
    }

    public void signupUser() {

        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {

                    String uid = auth.getCurrentUser().getUid();

                    User user = new User(name, email, "");

                    db.collection("users")
                            .document(uid)
                            .set(user)
                            .addOnSuccessListener(unused -> {

                                Toast.makeText(this, "Signup Success", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(this, JournalApp.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Database Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Signup Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
