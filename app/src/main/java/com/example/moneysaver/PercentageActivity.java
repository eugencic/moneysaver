package com.example.moneysaver;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

public class PercentageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);
    }

    public void backClicked(View view) {

        Intent intent=getIntent();
        String s= intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        if (s.equals("0"))  intent = new Intent(this, MainActivity.class);
        if (s.equals("1"))  intent = new Intent(this, SavingsActivity.class);
        startActivity(intent);
    }



}