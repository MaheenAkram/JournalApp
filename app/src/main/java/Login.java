package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView signup2 = findViewById(R.id.signup2);
        Button login = findViewById(R.id.login);
        signup2.setText(
                android.text.Html.fromHtml(
                        "Don't have an account? <font color='#40D0FF'>Sign Up</font>",
                        android.text.Html.FROM_HTML_MODE_LEGACY
                )
        );
        signup2.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignupActivity.class);
            startActivity(intent);
        });

        login.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, JournalApp.class);
            startActivity(intent);
        });



    }
}