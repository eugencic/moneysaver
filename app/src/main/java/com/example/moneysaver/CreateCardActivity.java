package com.example.moneysaver;


import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCardActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    SavCard savCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        savCard = new SavCard();

        Button buttonLink = findViewById(R.id.buttonLink);
        buttonLink.setOnClickListener(view -> LinkCard());
    }

    public void backClicked(View view) {
        onBackPressed();
    }

    private void LinkCard() {
        EditText savCardName = findViewById(R.id.editTextTextCardName);
        EditText savCardNr = findViewById(R.id.editTextTextCardNumber);
        EditText savCardDate = findViewById(R.id.editTextTextCardDate);
        EditText savCardYear = findViewById(R.id.editTextTextCardYear);

        String sCardName = savCardName.getText().toString();
        String sCardNr = savCardNr.getText().toString();
        String sCardDate = savCardDate.getText().toString();
        String sCardYear = savCardYear.getText().toString();

        if (TextUtils.isEmpty(sCardName) || sCardName.length() > 40) {
            savCardName.setError("The name must be between 1-39 characters long");
            savCardName.requestFocus();
        } else if (!savCard.checkLuhn(sCardNr)) {
            savCardNr.setError("Invalid credit card number");
            savCardNr.requestFocus();
        } else if (TextUtils.isEmpty(sCardDate) || sCardDate.length() > 2 || Integer.parseInt(sCardDate) > 12
                || Integer.parseInt(sCardDate) == 0) {
            savCardDate.setError("Expire month is wrong");
            savCardDate.requestFocus();
        } else if (TextUtils.isEmpty(sCardYear) || sCardYear.length() > 2) {
            savCardYear.setError("Set expire year as on the credit card");
            savCardYear.requestFocus();
        } else {
            savCard.Name = sCardName;
            savCard.Number = sCardNr;
            savCard.Month = sCardDate;
            savCard.Year = sCardYear;
            myRef.child("Savings Cards").child(currentUser.getUid()).setValue(savCard);
            Toast.makeText(CreateCardActivity.this, "Savings card linked successfully!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}