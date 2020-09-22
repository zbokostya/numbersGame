package com.zbokostya.numgames.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.zbokostya.numgames.R;


public class MainActivity extends AppCompatActivity {

    Button gameNormal;
    Button gameRandom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        gameNormal = findViewById(R.id.ButtonGameStandart);
        gameNormal.setOnClickListener(oclBtnNormal);

        gameRandom = findViewById(R.id.ButtonGameRandom);
        gameRandom.setOnClickListener(oclBtnRandom);

    }


    View.OnClickListener oclBtnNormal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("TypeOfGameChoosed", 1);
            startActivity(intent);
        }
    };

    View.OnClickListener oclBtnRandom = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("TypeOfGameChoosed", 2);
            startActivity(intent);
        }
    };

    View.OnClickListener oclBtnPrevious = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("TypeOfGameChoosed", 3);
            startActivity(intent);
        }
    };

}
