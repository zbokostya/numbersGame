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
import android.widget.LinearLayout;

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

    Button addButton;
    Button restartButton;

    int getGameType = 1;

    int NUMBER_NUMS = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getGameType = getIntent().getExtras().getInt("TypeOfGameChoosed");


        addButton = findViewById(R.id.addButtonActivity);
        addButton.setOnClickListener(oclBtnToAdd);


        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(oclBtnToRestart);


        initRecyclerView();

        startGame();

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    //adapter Init
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));
        adapter = new NumberAdapter();
        recyclerView.setAdapter(adapter);
    }

    //add Buttons
    private void addButtons(int n) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        for (int i = 0; i < n; i++) {
            Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 9, metrics.widthPixels / 9));
            btn.setId(i);
            btn.setText(arrInt.get(i) + "");
            btn.setBackgroundResource(R.drawable.button_border32);
            buttonsList.add(btn);
        }
        adapter.addItems(buttonsList);
    }

    private void addButton(int cnt) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Button btn = new Button(this);
        btn.setHeight(metrics.widthPixels / 9);
        btn.setId(cnt);
        btn.setText(arrInt.get(cnt) + "");
        btn.setBackgroundColor(Color.CYAN);
        buttonsList.add(btn);
    }


    //random init
    private void randomNums(int n) {
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            arrInt.add(rnd.nextInt(8) + 1);
        }
        adapter.addToIntArr(arrInt);
    }

    //standart init
    private void standartNums() {
        for (int i = 0; i < 9; i++) {
            arrInt.add(i + 1);
        }
        for (int i = 0; i < 9; i++) {
            arrInt.add(1);
            arrInt.add(i + 1);
        }
        adapter.addToIntArr(arrInt);
    }

    View.OnClickListener oclBtnToAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            arrInt.clear();
            arrInt.addAll(adapter.getIntArrayList());
            buttonsList.clear();
            buttonsList.addAll(adapter.getButtonsArrayList());
            addButtonsAndNums();
            adapter.setItems(buttonsList);
            adapter.setIntArrayList(arrInt);
        }
    };

    private void addButtonsAndNums() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int cnt = arrInt.size();
        int j = 0;
        for (int i = 0; i < cnt; i++) {
            if (arrInt.get(i) != 0) {
                arrInt.add(arrInt.get(i));
                Button btn = new Button(this);
                btn.setHeight(metrics.widthPixels / 9);
                btn.setWidth(metrics.widthPixels / 9);
                btn.setId(cnt + j);
                btn.setText(arrInt.get(cnt + j) + "");
                btn.setBackgroundResource(R.drawable.button_border32);
                buttonsList.add(btn);
                j++;
            }
        }
    }


    private void startGame() {
        if (getGameType == 1) {
            standartNums();
        }
        if (getGameType == 2) {
            randomNums(NUMBER_NUMS);
        }
        addButtons(NUMBER_NUMS);
    }

    //onRestart
    View.OnClickListener oclBtnToRestart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.removeAllItems();
            buttonsList.clear();
            arrInt.clear();
            startGame();

        }
    };
}
