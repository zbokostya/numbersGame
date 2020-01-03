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
import android.widget.Button;
import android.widget.TextView;

import com.zbokostya.numgames.Adapter.NumberAdapter;


public class MainActivity extends AppCompatActivity {

    private NumberAdapter adapter;
    private RecyclerView recyclerView;

    private TextView textViewTest;
    Button secondAct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secondAct = findViewById(R.id.ButtonSecondActivity);
        secondAct.setOnClickListener(oclBtn);

    }


    View.OnClickListener oclBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(intent);
        }
    };




}
