package com.example.moneysaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SavingsActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    User user;
    SavCard savCard;

    TextView cardName;
    TextView cardNumber;
    TextView cardDate;
    TextView cardSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        Objects.requireNonNull(getSupportActionBar()).hide();

        cardName = findViewById(R.id.card_name);
        cardNumber = findViewById(R.id.card_number);
        cardDate = findViewById(R.id.card_date);
        cardSaved = findViewById(R.id.card_saved);

        getData1();
        getData();
    }

    public void backClicked(View view) {
        onBackPressed();
    }

    public void percentClicked(View view) {
        Intent intent = new Intent(this, PercentageActivity.class);
        startActivity(intent);
    }

    private void getData1() {
        myRef.child("Savings Cards").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savCard = snapshot.getValue(SavCard.class);
                Objects.requireNonNull(savCard).Number = savCard.Number.replaceAll("(.{" + 4 + "})", "$1 ").trim();

                cardName.setText(savCard.Name);
                cardNumber.setText(savCard.Number);
                cardDate.setText(savCard.Month + " / " + savCard.Year);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SavingsActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        myRef.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                cardSaved.setText("MDL " + String.format("%.2f", Objects.requireNonNull(user).saved));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SavingsActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}