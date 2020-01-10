package com.zbokostya.numgames.GameLogic;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.Log;
import android.widget.Button;

import com.zbokostya.numgames.GameActivity;
import com.zbokostya.numgames.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;


public class GameLogic {
    private static GameLogic instance;

    private GameLogic() {
    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    private int setBackgroundCrossed = R.drawable.black_crossed;
    private int idFirstButtonPressed = -1;

    private void setLastPressedButtonId(int id) {
        idFirstButtonPressed = id;
    }

    public boolean ifGameEnded(ArrayList<Integer> intArrayList) {
        for (int el : intArrayList) {
            if (el != 0) return false;
        }
        return true;
    }


    public void setIdFirstButtonPressed(int n) {
        idFirstButtonPressed = n;
    }

    public void clearRows(ArrayList<Button> buttonsList, ArrayList<Integer> intArrayList) {
        int size = intArrayList.size();
        boolean flag = true;
        for (int i = 0; i < size / 9; i++) {
            flag = true;
            for (int j = 0; j < 9; j++) {
                if (intArrayList.get(i * 9 + j) != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                for (int j = 0; j < 9; j++) {
                    intArrayList.remove(i * 9 + j);
                    buttonsList.remove(i * 9 + j);
                }
            }
        }

    }

    public void mainActivator(int idSecond, ArrayList<Button> buttonsList, ArrayList<Integer> intArrayList) {
        //Log.d("1", idSecond + "" + intArrayList.get(idSecond));
        Button btn1;
        Button btn2;
        if (idFirstButtonPressed == -1) {
            btn1 = buttonsList.get(idSecond);
            btn1.setTextColor(Color.parseColor("#00574B"));
            btn1.setText(btn1.getText());
            buttonsList.set(idSecond, btn1);
            setLastPressedButtonId(idSecond);
            return;
        }
        if (idFirstButtonPressed == idSecond) {
            btn1 = buttonsList.get(idFirstButtonPressed);
            //btn1.setBackgroundColor(Color.CYAN);
            //btn1.setTextColor(Color.BLACK);
            buttonsList.set(idSecond, btn1);
            idFirstButtonPressed = -1;
            return;
        }
        btn1 = buttonsList.get(idFirstButtonPressed);
        btn2 = buttonsList.get(idSecond);
        if (delNum(idFirstButtonPressed, idSecond, intArrayList)) {
            intArrayList.set(idFirstButtonPressed, 0);
            intArrayList.set(idSecond, 0);
            btn1.setBackgroundResource(setBackgroundCrossed);
            btn2.setBackgroundResource(setBackgroundCrossed);
            buttonsList.set(idFirstButtonPressed, btn1);
            buttonsList.set(idSecond, btn2);
        } else {
            //btn1.setBackground(Color.CYAN);
            buttonsList.set(idFirstButtonPressed, btn1);
        }
        idFirstButtonPressed = -1;
    }

    private boolean deleteNumbers(int aId, int bId, ArrayList<Integer> intArrayList) {
        if (aId == bId) return false;
        if (aId > bId) {//if aId > bId swap to make | first < second
            int cnt = aId;
            aId = bId;
            bId = cnt;
        }

        //Если рядом(сверху вниз)
        if (bId - 9 == aId && (intArrayList.get(bId).equals(intArrayList.get(aId)) || intArrayList.get(bId) + intArrayList.get(aId) == 10)) {
            return true;
        }


        //Если рядом(слева на право)
        if (bId - 1 == aId && (intArrayList.get(bId).equals(intArrayList.get(aId)) || intArrayList.get(bId - 1) + intArrayList.get(aId) == 10)) {
            return true;
        }

        boolean flag = true;
        //проверяем на на наличие нулей между левый и правым
        for (int j = 0; j <= bId - aId - 2; j++) {
            if (!(intArrayList.get(aId + 1 + j) == 0)) {
                flag = false;
            }
        }
        if (flag) {
            return intArrayList.get(aId) + intArrayList.get(bId) == 10 || intArrayList.get(aId).equals(intArrayList.get(bId));
        }

        flag = true;
        //Проверка на нули сверху вниз
        int j;
        for (j = 0; j <= bId - aId - 18; j += 9) {
            if (!(intArrayList.get(aId + 9 + j) == 0)) {
                flag = false;
            }
        }
        if (flag && j != 0) {
            return intArrayList.get(aId) + intArrayList.get(bId) == 10 || intArrayList.get(aId).equals(intArrayList.get(bId));
        }

        return false;
    }


    public boolean delNum(int aId, int bId, ArrayList<Integer> intArrayList) {
        if (aId == bId) return false;
        if (aId > bId) {//if aId > bId swap to make | first < second
            int cnt = aId;
            aId = bId;
            bId = cnt;
        }
        boolean flag = true;
        if (intArrayList.get(aId).equals(intArrayList.get(bId)) || intArrayList.get(aId) + intArrayList.get(bId) == 10) {
            if (aId + 1 == bId || aId + 9 == bId) return true;
            for (int i = 1; aId + i < bId; i++) {
                if (intArrayList.get(aId + i) != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) return flag;
            flag = true;
            if ((bId - aId) % 9 != 0) return false;
            for (int i = 9; aId + i < bId; i += 9) {
                if (intArrayList.get(aId + i) != 0) flag = false;
            }
            return flag;
        } else return false;
    }

}
