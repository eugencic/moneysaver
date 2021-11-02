package com.example.moneysaver;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class SavingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void backClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void percentClicked(View view) {
        Intent intent = new Intent(this, PercentageActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE,"1");
        startActivity(intent);
    }
}