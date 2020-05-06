package com.zbokostya.numgames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private List<Button> buttons = new ArrayList<>();
    private ArrayList<Integer> numberButtons = new ArrayList<>();

    private int buttonBackground = R.drawable.white32;

    Button addButton;
    Button clearRowsButton;
    Button restartButton;

    int gameType = 1;// game type

    int NUMBER_NUMS = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_game);

        gameType = getIntent().getExtras().getInt("TypeOfGameChoosed");


        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(oclAddButton);

        clearRowsButton = findViewById(R.id.clearRowsButton);
        clearRowsButton.setOnClickListener(oclClearButton);

        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(oclBtnToRestart);


        initRecyclerView();

        startGame();

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
            btn.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 9, (int) (metrics.widthPixels / 9 * 0.85)));
            btn.setId(i);
            btn.setBackgroundResource(buttonBackground);
            String text = Integer.toString(numberButtons.get(i));
            btn.setText(text);
            buttons.add(btn);
        }
        adapter.addItems(buttons);
    }


    //random init
    private void randomNums(int n) {
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            numberButtons.add(rnd.nextInt(8) + 1);
        }
        adapter.addToIntArr(numberButtons);
    }

    //standart init
    private void standartNums() {
        for (int i = 0; i < 9; i++) {
            numberButtons.add(i + 1);
        }
        for (int i = 0; i < 8; i++) {
            numberButtons.add(1);
            numberButtons.add(i + 1);
        }
        adapter.addToIntArr(numberButtons);
    }



    private void addButtonsAndNums() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int cnt = numberButtons.size();
        int j = 0;
        for (int i = 0; i < cnt; i++) {
            if (numberButtons.get(i) != 0) {
                numberButtons.add(numberButtons.get(i));
                Button btn = new Button(this);
                btn.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 9, (int) (metrics.widthPixels / 9 * 0.85)));
                btn.setHeight(metrics.widthPixels / 9);
                btn.setWidth(metrics.widthPixels / 9);
                btn.setId(cnt + j);
                btn.setBackgroundResource(buttonBackground);
                btn.setText(numberButtons.get(cnt + j) + "");
                buttons.add(btn);
                j++;
            }
        }
    }


    private void startGame() {
        if (gameType == 1) {
            standartNums();
        }
        if (gameType == 2) {
            randomNums(NUMBER_NUMS);
        }
        addButtons(NUMBER_NUMS);
    }
    //Add
    View.OnClickListener oclAddButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            numberButtons.clear();
            numberButtons.addAll(adapter.getIntArrayList());
            buttons.clear();
            buttons.addAll(adapter.getButtonsArrayList());
            addButtonsAndNums();
            adapter.setItems(buttons);
            adapter.setIntArrayList(numberButtons);
        }
    };

    //Clear rows
    View.OnClickListener oclClearButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.clearRows();
            numberButtons.clear();
            numberButtons.addAll(adapter.getIntArrayList());
            buttons.clear();
            buttons.addAll(adapter.getButtonsArrayList());
        }
    };

    //onRestart
    View.OnClickListener oclBtnToRestart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.removeAllItems();
            buttons.clear();
            numberButtons.clear();
            startGame();

        }
    };

}
