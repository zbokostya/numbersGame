package com.zbokostya.numgames.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zbokostya.numgames.Adapter.NumberAdapter;
import com.zbokostya.numgames.R;
import com.zbokostya.numgames.enums.intValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private NumberAdapter adapter;
    private RecyclerView recyclerView;


    //buttons list
    public static List<Button> buttons = new ArrayList<>();
    private ArrayList<Integer> numberButtons = new ArrayList<>();

    private int buttonBackground = R.drawable.white32;

    Button addButton;
    Button clearRowsButton;
    Button restartButton;
    Button hintButton;

    ToggleButton expandButton;
    TextView gameInfo;

    int gameType = 3;// game type

    //total buttons on start
    private int NUMBER_NUMS = intValues.NUMBER_NUMS.getValue();
    //buttons in line
    private int spanCount = intValues.spanCount.getValue();

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

        hintButton = findViewById(R.id.hintButton);
        hintButton.setOnClickListener(oclBtnHint);


//        expandButton = findViewById(R.id.toggleButton);


        initRecyclerView();

        //start game
        startGame();

    }

    @Override
    protected void onPause() {
        super.onPause();
        writePrimitiveInternalMemory("prev", numberButtons);
    }

    //adapter Init
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        adapter = new NumberAdapter();
        recyclerView.setAdapter(adapter);
    }

    //add Buttons
    private void addStartButtons(int n) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        for (int i = 0; i < n; i++) {
            Button btn = new Button(this);
            //width of button
            //height = 0.85 width
            int width = metrics.widthPixels / spanCount;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (int) (width * 0.85));
            params.bottomMargin = 2;
            btn.setLayoutParams(params);
            btn.setId(i);
            btn.setBackgroundResource(buttonBackground);
            String text = Integer.toString(numberButtons.get(i));
            btn.setText(text);
            buttons.add(btn);
        }
        adapter.addItems(buttons);
    }


    //random init
    private void randomNumbersInit(int n) {
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            numberButtons.add(rnd.nextInt(8) + 1);
        }
        adapter.addToIntArr(numberButtons);
    }

    //normal init
    private void normalNumbersInit() {
        for (int i = 0; i < 9; i++) {
            numberButtons.add(i + 1);
        }
        for (int i = 0; i < 8; i++) {
            numberButtons.add(1);
            numberButtons.add(i + 1);
        }
        adapter.addToIntArr(numberButtons);
    }


    private void previousNumbersInit() {
        numberButtons = readPrimitiveInternalMemoryInteger("prev");
        adapter.addToIntArr(numberButtons);
    }

    //after add clicked
    private void onAddClickedAddButtonsAndNumbers() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int allButtonsNumber = numberButtons.size();
        int j = 0;
//        int clearedNums = 0;
//        for (int i = 0; i < allButtonsNumber; i++) {
//            if (numberButtons.get(i) == -1) clearedNums++;
//        }

        for (int i = 0; i < allButtonsNumber; i++) {
            if (numberButtons.get(i) != 0 && numberButtons.get(i) != -1) {
                numberButtons.add(numberButtons.get(i));
                Button btn = new Button(this);
                int width = metrics.widthPixels / spanCount;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (int) (width * 0.85));
                params.bottomMargin = 2;
                btn.setLayoutParams(params);
//                btn.setHeight(width);
//                btn.setWidth(width);
                btn.setId(allButtonsNumber + j);
                btn.setBackgroundResource(buttonBackground);
                btn.setText(numberButtons.get(allButtonsNumber + j).toString());
                buttons.add(btn);
                j++;
            }
        }
    }


    private void startGame() {
        if (gameType == 1) {
            normalNumbersInit();
            addStartButtons(NUMBER_NUMS);
        } else if (gameType == 2) {
            randomNumbersInit(NUMBER_NUMS);
            addStartButtons(NUMBER_NUMS);
        } else if (gameType == 3) {
            previousNumbersInit();
            addStartButtons(numberButtons.size());
        }

    }

    //Add
    View.OnClickListener oclAddButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            numberButtons.clear();
            numberButtons.addAll(adapter.getIntegerArrayList());
            buttons.clear();
            buttons.addAll(adapter.getButtonsArrayList());

            onAddClickedAddButtonsAndNumbers();
            adapter.setItems(buttons);
            adapter.setIntegerArrayList(numberButtons);
        }
    };

    //Clear rows
    View.OnClickListener oclClearButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.clearRows();
            numberButtons.clear();
            numberButtons.addAll(adapter.getIntegerArrayList());
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

    //onHint
    View.OnClickListener oclBtnHint = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.hintButton();
        }
    };



    public void writePrimitiveInternalMemory(String key, List<Integer> value) {
        Integer[] myIntList = value.toArray(new Integer[value.size()]);

        SharedPreferences preferences = this.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, TextUtils.join("‚‗‚", myIntList));
        editor.apply();
        Log.d("write", TextUtils.join("‚‗‚", myIntList));
    }

    public ArrayList<Integer> readPrimitiveInternalMemoryInteger(String key) {
        SharedPreferences preferences = this.getPreferences(Activity.MODE_PRIVATE);

        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<>(Arrays.asList(myList));
        ArrayList<Integer> newList = new ArrayList<>();
        for (String item : arrayToList)
            newList.add(Integer.parseInt(item));
        Log.d("read", newList.toString());
        return newList;
    }


}
