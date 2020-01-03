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

    //buttons id wich pressed
    private int iDfirstButtonClickedToDel = -1;
    private int greenButtonId = -1;

    //buttons int array
    private ArrayList<Integer> arr = new ArrayList<>();

    //buttons list
    private List<Button> buttonsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initRecyclerView();
        randomNums(18);
        addButton(18);

    }

    //add Buttons
    private void addButton(int n) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int cnt = buttonsList.size();
        cnt = 0;
        for (int i = 0; i < n; i++) {
            Button btn = new Button(this);
            btn.setHeight(metrics.widthPixels / 9);
            btn.setId(cnt + i );
            //btn.setText(arr.get(cnt + i ));
            //btn.setOnClickListener(oclBtnToTest);
            buttonsList.add(btn);
        }
        adapter.addItems(buttonsList);
    }

    //adapter Init
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));
        adapter = new NumberAdapter();
        recyclerView.setAdapter(adapter);
    }

    //random add numbers
    private void randomNums(int n) {
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            arr.add(rnd.nextInt(8) + 1);
        }
    }

    //add numbers
    private void addNums() {
        int cnt = arr.size();
        for (int i = 0; i < cnt; i++) {
            if (arr.get(i) != 0) {
                arr.add(arr.get(i));
            }
        }
    }

    //listener to buttons
    public void listener(int butId) {
        Log.d("13",butId+"");
    }

    public void list(int n){
        Log.d("list", n+"");
    }

    private void del12(int idButtonSecond) {
        Button btn1;
        Button btn2;

        //set first click button id and make green
        if (iDfirstButtonClickedToDel == -1 && arr.get(idButtonSecond - 256) != 0) {
            iDfirstButtonClickedToDel = idButtonSecond;
            greenButtonId = iDfirstButtonClickedToDel;
            btn1 = findViewById(iDfirstButtonClickedToDel);
            btn1.setBackgroundColor(Color.GREEN);
            return;
        }
        //make cyan again after green
        if (greenButtonId == idButtonSecond && iDfirstButtonClickedToDel != -1) {
            btn1 = findViewById(greenButtonId);
            btn1.setBackgroundColor(Color.CYAN);
            iDfirstButtonClickedToDel = -1;
            greenButtonId = -1;
            return;
        }
        //make first button green
        btn1 = findViewById(iDfirstButtonClickedToDel);
        btn2 = findViewById(idButtonSecond);
        if (iDfirstButtonClickedToDel == -1) return;
        if (deleteNumbers(iDfirstButtonClickedToDel - 256, idButtonSecond - 256)) {
            arr.set(idButtonSecond - 256, 0);
            arr.set(iDfirstButtonClickedToDel - 256, 0);
            btn1.setText("0");
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setText("0");
            btn2.setBackgroundColor(Color.BLACK);
            iDfirstButtonClickedToDel = -1;
        } else {
            btn1 = findViewById(greenButtonId);
            btn1.setBackgroundColor(Color.CYAN);
            iDfirstButtonClickedToDel = -1;
            greenButtonId = -1;
        }

    }

    private boolean deleteNumbers(int aId, int bId) {
        if (aId == bId) return false;
        if (aId > bId) {//if aId > bId swap to make | first < second
            int cnt = aId;
            aId = bId;
            bId = cnt;
        }

        //Если рядом(сверху вниз)
        if (bId - 9 == aId && (arr.get(bId).equals(arr.get(aId)) || arr.get(bId) + arr.get(aId) == 10)) {
            return true;
        }


        //Если рядом(слева на право)
        if (bId - 1 == aId && (arr.get(bId).equals(arr.get(aId)) || arr.get(bId - 1) + arr.get(aId) == 10)) {
            return true;
        }

        boolean flag = true;
        //проверяем на на наличие нулей между левый и правым
        for (int j = 0; j <= bId - aId - 2; j++) {
            if (!(arr.get(aId + 1 + j) == 0)) {
                flag = false;
            }
        }
        if (flag) {
            return arr.get(aId) + arr.get(bId) == 10 || arr.get(aId).equals(arr.get(bId));
        }

        flag = true;
        //Проверка на нули сверху вниз
        int j;
        for (j = 0; j <= bId - aId - 18; j += 9) {
            if (!(arr.get(aId + 9 + j) == 0)) {
                flag = false;
            }
        }
        if (flag && j != 0) {
            return arr.get(aId) + arr.get(bId) == 10 || arr.get(aId).equals(arr.get(bId));
        }

        return false;
    }
}
