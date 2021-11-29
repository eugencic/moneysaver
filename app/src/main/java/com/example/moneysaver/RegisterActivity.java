package com.example.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etRegCardNumber;
    TextInputEditText etRegCardDate;
    TextInputEditText etRegCardYear;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseAuth mAuth;

    Card mainCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        etRegCardNumber = findViewById(R.id.etRegCardNr);
        etRegCardDate = findViewById(R.id.etRegCardDate);
        etRegCardYear = findViewById(R.id.etRegCardYear);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mainCard = new Card();

        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(view -> createUser());

        tvLoginHere.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void createUser() {
        String email = Objects.requireNonNull(etRegEmail.getText()).toString();
        String password = Objects.requireNonNull(etRegPassword.getText()).toString();
        String cardNr = Objects.requireNonNull(etRegCardNumber.getText()).toString();
        String cardDate = Objects.requireNonNull(etRegCardDate.getText()).toString();
        String cardYear = Objects.requireNonNull(etRegCardYear.getText()).toString();

        if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        } else if (TextUtils.isEmpty(cardNr) || !mainCard.checkLuhn(cardNr)) {
            etRegCardNumber.setError("Introduce a valid credit card number");
            etRegCardNumber.requestFocus();
        } else if (TextUtils.isEmpty(cardDate) || cardDate.length() > 2 || Integer.parseInt(cardDate) > 12
                || Integer.parseInt(cardDate) == 0) {
            etRegCardDate.setError("Expire month is wrong");
            etRegCardDate.requestFocus();
        } else if (TextUtils.isEmpty(cardYear) || cardYear.length() > 2) {
            etRegCardYear.setError("Set Expire Year as on the credit card");
            etRegCardYear.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Calendar calendar = Calendar.getInstance();
                    User user = new User();
                    user.balance = 5050.25f;
                    user.prev_balance = user.balance;
                    user.benefits = 0f;
                    user.pays = 0f;
                    user.monthSet = calendar.get(Calendar.MONTH);

                    myRef.child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).setValue(user);

                    mainCard.Number = cardNr;
                    mainCard.Month = cardDate;
                    mainCard.Year = cardYear;
                    myRef.child("Main Cards").child(mAuth.getCurrentUser().getUid()).setValue(mainCard);

                    SavCard savingsCard = new SavCard();
                    myRef.child("Savings Cards").child(mAuth.getCurrentUser().getUid()).setValue(savingsCard);

                    Toast toast = Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_LONG);
                    toast.getView().setBackgroundResource(R.drawable.toast_blue);
                    toast.show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast toast = Toast.makeText(RegisterActivity.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundResource(R.drawable.toast_red);
                    toast.show();
                }
            });
        }
    }
}