package com.zbokostya.numgames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zbokostya.numgames.Adapter.NumberAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private NumberAdapter adapter;
    private RecyclerView recyclerView;

    //buttons list
    private List<Button> buttonsList = new ArrayList<>();
    private ArrayList<Integer> arrInt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initRecyclerView();
        randomNums(18);
        addButton(18);
    }

    //adapter Init
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));
        adapter = new NumberAdapter();
        recyclerView.setAdapter(adapter);
    }

    //add Buttons
    private void addButton(int n) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        for (int i = 0; i < n; i++) {
            Button btn = new Button(this);
            btn.setHeight(metrics.widthPixels / 9);
            btn.setId(i);
            btn.setText(arrInt.get(i) + "");
            buttonsList.add(btn);
        }
        adapter.addItems(buttonsList);
    }

    protected void randomNums(int n) {
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            arrInt.add(rnd.nextInt(8) + 1);
        }
    }


}
