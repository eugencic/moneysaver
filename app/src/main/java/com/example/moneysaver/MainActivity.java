package com.example.moneysaver;


import android.annotation.SuppressLint;
import android.content.Intent;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

interface EventListener {
    void updateData(float balance, float benefits, float pays);
    void toastNotEnough();
    void toastDataFail();
}

public class MainActivity extends AppCompatActivity implements EventListener {
    private TextView balanceView;
    private TextView balanceView1;
    private TextView paysView;

    ImageButton im4;
    ImageButton im5;

    BalanceManager balanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        balanceManager = new BalanceManager();
        EventListener mListener = this;
        balanceManager.setEventListener(mListener);

        balanceView = findViewById(R.id.balanc);
        balanceView1 = findViewById(R.id.bal);
        paysView = findViewById(R.id.pays);

        im4 = findViewById(R.id.imageButton4);
        im5 = findViewById(R.id.imageButton5);

        balanceManager.getSavCardData();
        balanceManager.getData();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void updateData(float balance, float benefits, float pays) {
        balanceView.setText(String.format("%.2f", balance) + " MDL");
        balanceView1.setText(String.format("%.2f", benefits) + " MDL");
        paysView.setText(String.format("%.2f", pays) + " MDL");
    }

    @Override
    public void toastNotEnough() {
        Toast toast = Toast.makeText(this, "Not enough funds to save money from the last transaction", Toast.LENGTH_LONG);
        toast.getView().setBackgroundResource(R.drawable.toast_red);
        toast.show();
    }

    @Override
    public void toastDataFail() {
        Toast toast = Toast.makeText(this, "Failed to get data", Toast.LENGTH_LONG);
        toast.getView().setBackgroundResource(R.drawable.toast_red);
        toast.show();
    }

    public void savingsClicked(View view) {
        if (balanceManager.savCard.Name == null) {
            Toast toast = Toast.makeText(MainActivity.this, "Link a savings card", Toast.LENGTH_LONG);
            toast.getView().setBackgroundResource(R.drawable.toast_red);
            toast.show();
        } else if (balanceManager.savCard.percent == 0) {
            Toast toast = Toast.makeText(MainActivity.this, "Choose the savings percent", Toast.LENGTH_LONG);
            toast.getView().setBackgroundResource(R.drawable.toast_red);
            toast.show();
        } else {
            Intent intent = new Intent(this, SavingsActivity.class);
            startActivity(intent);
        }
    }

    public void createClicked(View view) {
        if (balanceManager.savCard.Name == null) {
            Intent intent = new Intent(this, CreateCardActivity.class);
            startActivity(intent);
        }
    }

    public void percentageClicked(View view) {
        if (balanceManager.savCard.Name != null) {
            Intent intent = new Intent(this, PercentageActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(MainActivity.this, "Link a savings card", Toast.LENGTH_LONG);
            toast.getView().setBackgroundResource(R.drawable.toast_red);
            toast.show();
        }
    }

    public void settingsClicked(View view) {
        Intent intent = new Intent(this, LogoutActivity.class);
        startActivity(intent);
    }
}