package com.example.moneysaver;


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

    private EventListener mListener;

    public void setEventListener(EventListener mListener) {
        this.mListener = mListener;
    }

    public void getData() {
        myRef.child("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {

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
                            mListener.toastNotEnough();
                        } else {
                            user.pays = user.pays - rate - savCard.percent / 100 * rate;
                            user.saved -= savCard.percent / 100 * rate;
                            user.balance += savCard.percent / 100 * rate;
                        }
                    }
                    user.prev_balance = user.balance;
                }
                myRef.child("Users").child(currentUser.getUid()).setValue(user);

                mListener.updateData(user.balance, user.benefits, user.pays);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.toastDataFail();
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
                mListener.toastDataFail();
            }
        });
    }
}