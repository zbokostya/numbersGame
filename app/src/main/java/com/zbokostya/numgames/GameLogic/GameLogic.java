package com.zbokostya.numgames.GameLogic;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

import com.zbokostya.numgames.GameActivity;

import java.util.ArrayList;
import java.util.List;


public class GameLogic {
    private static GameLogic instance;
    private GameLogic() { }
    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    private int idFirstButtonPressed = -1;
    private ArrayList<Button> buttonsList = new ArrayList<>();

    //buttons int array
    private ArrayList<Integer> arrInt = new ArrayList<>();


    public void initArrays(List<Button> listButton, List<Integer> listInteger) {
        setListButtons(listButton);
        setListInt(listInteger);
    }

    private void setListButtons(List<Button> list) {
        buttonsList.addAll(list);
    }

    private void setListInt(List<Integer> list) {
        arrInt.addAll(list);
    }

    private void setLastPressedButtonId(int id) {
        idFirstButtonPressed = id;
    }

    public ArrayList<Integer> returnIntArray() {
        return arrInt;
    }

    public ArrayList<Button> returnButtonArray() {
        return buttonsList;
    }

    public void mainActivator(int idSecond) {
        Log.d("1", idFirstButtonPressed + "");
        Log.d("2", idSecond + "");
        Button btn1;
        Button btn2;
        if (idFirstButtonPressed == -1) {
            btn1 = buttonsList.get(idSecond);
            btn1.setBackgroundColor(Color.GREEN);
            setLastPressedButtonId(idSecond);
            return;
        }
        if (idFirstButtonPressed == idSecond && idFirstButtonPressed != -1) {
            btn1 = buttonsList.get(idFirstButtonPressed);
            btn1.setBackgroundColor(Color.CYAN);
            idFirstButtonPressed = -1;
            return;
        }
        btn1 = buttonsList.get(idFirstButtonPressed);
        btn2 = buttonsList.get(idSecond);
        if (deleteNumbers(idFirstButtonPressed, idSecond)) {
            arrInt.set(idFirstButtonPressed, 0);
            arrInt.set(idSecond, 0);
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
        } else {
            btn1.setBackgroundColor(Color.CYAN);
        }
        idFirstButtonPressed = -1;
    }

    private boolean deleteNumbers(int aId, int bId) {
        if (aId == bId) return false;
        if (aId > bId) {//if aId > bId swap to make | first < second
            int cnt = aId;
            aId = bId;
            bId = cnt;
        }

        //Если рядом(сверху вниз)
        if (bId - 9 == aId && (arrInt.get(bId).equals(arrInt.get(aId)) || arrInt.get(bId) + arrInt.get(aId) == 10)) {
            return true;
        }


        //Если рядом(слева на право)
        if (bId - 1 == aId && (arrInt.get(bId).equals(arrInt.get(aId)) || arrInt.get(bId - 1) + arrInt.get(aId) == 10)) {
            return true;
        }

        boolean flag = true;
        //проверяем на на наличие нулей между левый и правым
        for (int j = 0; j <= bId - aId - 2; j++) {
            if (!(arrInt.get(aId + 1 + j) == 0)) {
                flag = false;
            }
        }
        if (flag) {
            return arrInt.get(aId) + arrInt.get(bId) == 10 || arrInt.get(aId).equals(arrInt.get(bId));
        }

        flag = true;
        //Проверка на нули сверху вниз
        int j;
        for (j = 0; j <= bId - aId - 18; j += 9) {
            if (!(arrInt.get(aId + 9 + j) == 0)) {
                flag = false;
            }
        }
        if (flag && j != 0) {
            return arrInt.get(aId) + arrInt.get(bId) == 10 || arrInt.get(aId).equals(arrInt.get(bId));
        }

        return false;
    }



    /*//buttons id wich pressed
    private int iDfirstButtonClickedToDel = -1;
    private int greenButtonId = -1;

    //buttons int array
    private ArrayList<Integer> arr = new ArrayList<>();

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


*/
}
