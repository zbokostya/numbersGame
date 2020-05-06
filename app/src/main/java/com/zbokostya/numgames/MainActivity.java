package com.zbokostya.numgames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zbokostya.numgames.Adapter.NumberAdapter;


public class MainActivity extends AppCompatActivity {

    private NumberAdapter adapter;
    private RecyclerView recyclerView;

    private TextView textViewTest;
    Button gameStandart;
    Button gameRandom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        gameStandart = findViewById(R.id.ButtonGameStandart);
        gameStandart.setOnClickListener(oclBtnStandart);
        gameRandom = findViewById(R.id.ButtonGameRandom);
        gameRandom.setOnClickListener(oclBtnRandom);

    }


    View.OnClickListener oclBtnStandart = new View.OnClickListener() {
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

}
