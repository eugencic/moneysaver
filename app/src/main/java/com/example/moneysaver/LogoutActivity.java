package com.example.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LogoutActivity extends AppCompatActivity {
    Button btnLogOut, btnBack;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnLogOut = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(LogoutActivity.this, LoginActivity.class));
        });

        btnBack.setOnClickListener(view -> onBackPressed());
    }
}