package com.example.moneysaver;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PercentageActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    SavCard savCard;

    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    Slider slider;
    float val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);
        Objects.requireNonNull(getSupportActionBar()).hide();

        imageButton1 = findViewById(R.id.radio1);
        imageButton2 = findViewById(R.id.radio2);
        imageButton3 = findViewById(R.id.radio3);
        slider = findViewById(R.id.slider);

        slider.addOnChangeListener((slider, value, fromUser) -> val = value);

        getData2();
    }

    public void backClicked(View view) {
        onBackPressed();
    }

    public void radio1Clicked(View view) {
        if (!imageButton1.isSelected()) {
            imageButton1.setBackgroundResource(R.drawable.round_corner1);
            imageButton1.setSelected(true);
            imageButton2.setBackgroundResource(R.drawable.round_corner);
            imageButton2.setSelected(false);
            imageButton3.setBackgroundResource(R.drawable.round_corner);
            imageButton3.setSelected(false);
        }
    }

    public void radio2Clicked(View view) {
        if (!imageButton2.isSelected()) {
            imageButton1.setBackgroundResource(R.drawable.round_corner);
            imageButton1.setSelected(false);
            imageButton2.setBackgroundResource(R.drawable.round_corner1);
            imageButton2.setSelected(true);
            imageButton3.setBackgroundResource(R.drawable.round_corner);
            imageButton3.setSelected(false);
        }
    }

    public void radio3Clicked(View view) {
        if (!imageButton3.isSelected()) {
            imageButton1.setBackgroundResource(R.drawable.round_corner);
            imageButton1.setSelected(false);
            imageButton2.setBackgroundResource(R.drawable.round_corner);
            imageButton2.setSelected(false);
            imageButton3.setBackgroundResource(R.drawable.round_corner1);
            imageButton3.setSelected(true);
        }
    }

    public void SaveClicked(View view) {
        boolean selected = imageButton1.isSelected() || imageButton2.isSelected() || imageButton3.isSelected();

        if (!selected) {
            Toast.makeText(PercentageActivity.this, "Select an option", Toast.LENGTH_SHORT).show();
        } else {
            if (imageButton1.isSelected()) {
                savCard.percentSelected = 1;
                savCard.percent = 1;
                myRef.child("Savings Cards").child(currentUser.getUid()).setValue(savCard);
            }

            if (imageButton2.isSelected()) {
                savCard.percentSelected = 2;
                savCard.percent = 5;
                myRef.child("Savings Cards").child(currentUser.getUid()).setValue(savCard);
            }

            if (imageButton3.isSelected()) {
                savCard.percentSelected = 3;
                savCard.percent = val;
                myRef.child("Savings Cards").child(currentUser.getUid()).setValue(savCard);
            }

            backClicked(view);
        }
    }

    private void getData2() {
        myRef.child("Savings Cards").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savCard = snapshot.getValue(SavCard.class);

                switch (Objects.requireNonNull(savCard).percentSelected) {
                    case 1:
                        imageButton1.setBackgroundResource(R.drawable.round_corner1);
                        imageButton1.setSelected(true);
                        imageButton2.setBackgroundResource(R.drawable.round_corner);
                        imageButton2.setSelected(false);
                        imageButton3.setBackgroundResource(R.drawable.round_corner);
                        imageButton3.setSelected(false);
                        break;
                    case 2:
                        imageButton1.setBackgroundResource(R.drawable.round_corner);
                        imageButton1.setSelected(false);
                        imageButton2.setBackgroundResource(R.drawable.round_corner1);
                        imageButton2.setSelected(true);
                        imageButton3.setBackgroundResource(R.drawable.round_corner);
                        imageButton3.setSelected(false);
                        break;
                    case 3:
                        imageButton1.setBackgroundResource(R.drawable.round_corner);
                        imageButton1.setSelected(false);
                        imageButton2.setBackgroundResource(R.drawable.round_corner);
                        imageButton2.setSelected(false);
                        imageButton3.setBackgroundResource(R.drawable.round_corner1);
                        imageButton3.setSelected(true);
                        slider.setValue(savCard.percent);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PercentageActivity.this, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}