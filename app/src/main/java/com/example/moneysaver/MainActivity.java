package com.example.moneysaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    public static final String EXTRA_MESSAGE = "com.example.moneysaver.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navbar);

        bottomNavigationView.setSelectedItemId(R.id.invisible);

        Objects.requireNonNull(getSupportActionBar()).hide();

        float balance=0.00f;
        float pays=3710.00f;

        @SuppressLint("DefaultLocale") String bal=String.format("%.2f", balance);
        @SuppressLint("DefaultLocale") String p= String.format("%.2f", pays);

        TextView balanceView= findViewById(R.id.balance);
        balanceView.setText( bal+" MDL");

        TextView balanceView1= findViewById(R.id.bal);
        balanceView1.setText( bal+" MDL");

        TextView paysView= findViewById(R.id.pays);
        paysView.setText( p+" MDL");

    }


    public void savingsClicked(View view) {
        Intent intent = new Intent(this, SavingsActivity.class);
        startActivity(intent);
    }

    public void createClicked(View view) {
        Intent intent = new Intent(this, CreateCardActivity.class);
        startActivity(intent);
    }

    public void percentageClicked(View view) {
        Intent intent = new Intent(this, PercentageActivity.class);
        intent.putExtra(EXTRA_MESSAGE,"0");
        startActivity(intent);
    }

}