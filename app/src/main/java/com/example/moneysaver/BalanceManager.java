package com.example.moneysaver;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BalanceManager {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    User user;
    SavCard savCard;

    Context context;

    public void getData() {
        myRef.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                Calendar calendar = Calendar.getInstance();

                if (calendar.get(Calendar.MONTH) != user.monthSet) {
                    user.monthSet = calendar.get(Calendar.MONTH);
                    user.pays = 0;
                    user.benefits = 0;
                }

                if (user.balance != user.prev_balance) {
                    float rate = user.balance - user.prev_balance;

                    if (rate > 0) {
                        user.benefits += rate;
                    } else if (rate < 0) {
                        if (user.balance + savCard.percent / 100 * rate < 0) {
                            user.pays = user.pays - rate;
                            Toast toast = Toast.makeText(context, "Not enough funds to save money from the last transaction", Toast.LENGTH_LONG);
                            toast.getView().setBackgroundResource(R.drawable.toast_red);
                            toast.show();
                        } else {
                            user.pays = user.pays - rate - savCard.percent / 100 * rate;
                            user.saved -= savCard.percent / 100 * rate;
                            user.balance += savCard.percent / 100 * rate;
                        }
                    }

                    user.prev_balance = user.balance;
                }

                myRef.child("Users").child(currentUser.getUid()).setValue(user);

                MainActivity.balanceView.setText(String.format("%.2f", user.balance) + " MDL");
                MainActivity.balanceView1.setText(String.format("%.2f", user.benefits) + " MDL");
                MainActivity.paysView.setText(String.format("%.2f", user.pays) + " MDL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(context, "Failed to get data", Toast.LENGTH_LONG);
                toast.getView().setBackgroundResource(R.drawable.toast_red);
                toast.show();
            }
        });
    }

    public void getSavCardData() {
        myRef.child("Savings Cards").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savCard = snapshot.getValue(SavCard.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(context, "Failed to get data", Toast.LENGTH_LONG);
                toast.getView().setBackgroundResource(R.drawable.toast_red);
                toast.show();
            }
        });
    }
}