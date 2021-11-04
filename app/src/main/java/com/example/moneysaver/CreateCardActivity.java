package com.example.moneysaver;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CreateCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);


    }

    public void backClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}