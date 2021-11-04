package com.example.moneysaver;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class PercentageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);
    }

    public void backClicked(View view) {

        Intent intent = getIntent();
        String s = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        if (s.equals("0")) intent = new Intent(this, MainActivity.class);
        if (s.equals("1")) intent = new Intent(this, SavingsActivity.class);
        startActivity(intent);
    }


    public void radio1Clicked(View view) {

        ImageButton imageButton1 = findViewById(R.id.radio1);



        if (!imageButton1.isSelected()) {
            imageButton1.setBackgroundResource(R.drawable.round_corner1);
            imageButton1.setSelected(true);
            ImageButton imageButton2 = findViewById(R.id.radio2);
            ImageButton imageButton3 = findViewById(R.id.radio3);
            imageButton2.setBackgroundResource(R.drawable.round_corner);
            imageButton2.setSelected(false);
            imageButton3.setBackgroundResource(R.drawable.round_corner);
            imageButton3.setSelected(false);


        }

    }




    public void radio2Clicked(View view) {

        ImageButton imageButton2 = findViewById(R.id.radio2);

        if (!imageButton2.isSelected()) {
            ImageButton imageButton1 = findViewById(R.id.radio1);
            imageButton1.setBackgroundResource(R.drawable.round_corner);
            imageButton1.setSelected(false);
            imageButton2.setBackgroundResource(R.drawable.round_corner1);
            imageButton2.setSelected(true);
            ImageButton imageButton3 = findViewById(R.id.radio3);
            imageButton3.setBackgroundResource(R.drawable.round_corner);
            imageButton3.setSelected(false);


        }

    }



    public void radio3Clicked(View view) {


        ImageButton imageButton3 = findViewById(R.id.radio3);


        if (!imageButton3.isSelected()) {
            ImageButton imageButton1 = findViewById(R.id.radio1);
            ImageButton imageButton2 = findViewById(R.id.radio2);
            imageButton1.setBackgroundResource(R.drawable.round_corner);
            imageButton1.setSelected(false);
            imageButton2.setBackgroundResource(R.drawable.round_corner);
            imageButton2.setSelected(false);
            imageButton3.setBackgroundResource(R.drawable.round_corner1);
            imageButton3.setSelected(true);


        }

    }

    public void SaveClicked(View view){

        ImageButton imageButton1 = findViewById(R.id.radio1);
        ImageButton imageButton2 = findViewById(R.id.radio2);
        ImageButton imageButton3 = findViewById(R.id.radio3);

        boolean selected= imageButton1.isSelected() || imageButton2.isSelected() || imageButton3.isSelected();

        if(!selected){
            Toast.makeText(PercentageActivity.this,"Select an option",Toast.LENGTH_SHORT).show();
        }
        else
        {
            backClicked(view);
        }


    }

}