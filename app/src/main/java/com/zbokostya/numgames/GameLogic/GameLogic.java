package com.zbokostya.numgames.GameLogic;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

import com.zbokostya.numgames.R;

import java.util.ArrayList;


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

    //drawables
    private int setBackgroundCrossed = R.drawable.button_border32;
    private int BLACK = Color.BLACK;
    private int pressedColor = Color.GRAY;

    private int idFirstButton = -1;


    public boolean ifGameEnded(ArrayList<Integer> intArrayList) {
        for (int el : intArrayList) {
            if (el != 0) return false;
        }
        return true;
    }


    public void setIdFirstButton(int n) {
        idFirstButton = n;
    }

    public void clearRows(ArrayList<Button> buttonsList, ArrayList<Integer> intArrayList) {
        int size = intArrayList.size();
        boolean flag;
        for (int i = 0; i < size / 9; i++) {
            flag = true;
            for (int j = 0; j < 9; j++) {
                if (!(intArrayList.get(i * 9 + j) == 0 || intArrayList.get(i * 9 + j) == -1)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                for (int j = 0; j < 9; j++) {
                    buttonsList.remove(i * 9);
                    //intArrayList.remove(i * 9);
                    intArrayList.set(i * 9 + j, -1);//-1 not counting
                }
            }
        }
    }

    public void mainActivator(int idSecondButton, ArrayList<Button> buttonsList, ArrayList<Integer> intArrayList) {
        Button firstButton;
        Button secondButton;
        //Log.d("text", idSecondButton + "");
        /*int cnt = 0;
        for (int i = 0; i < idSecondButton; i++) {
            if (intArrayList.get(i) == -1) {
                cnt++;
            }
        }
        idSecondButton -= cnt;*/
        if (intArrayList.get(idSecondButton) == 0) return;
        if (idFirstButton == -1) {
            firstButton = buttonsList.get(idSecondButton);
            firstButton.setTextColor(pressedColor);
            buttonsList.set(idSecondButton, firstButton);
            idFirstButton = idSecondButton;
            return;
        }
        if (idFirstButton == idSecondButton) {
            firstButton = buttonsList.get(idFirstButton);
            firstButton.setTextColor(BLACK);
            buttonsList.set(idSecondButton, firstButton);
            idFirstButton = -1;
            return;
        }
        firstButton = buttonsList.get(idFirstButton);
        secondButton = buttonsList.get(idSecondButton);

        firstButton.setTextColor(BLACK);
        secondButton.setTextColor(BLACK);
        //idSecondButton+=cnt;

        if (deleteNumbers()) {
            intArrayList.set(idFirstButton, 0);
            intArrayList.set(idSecondButton, 0);

            firstButton.setBackgroundResource(setBackgroundCrossed);
            secondButton.setBackgroundResource(setBackgroundCrossed);

            firstButton.setText(Integer.toString(idFirstButton));
            secondButton.setText(Integer.toString(idSecondButton));

            buttonsList.set(idFirstButton, firstButton);
            buttonsList.set(idSecondButton, secondButton);
        } else {
            buttonsList.set(idFirstButton, firstButton);
            buttonsList.set(idSecondButton, secondButton);
        }
        idFirstButton = -1;
    }

    private boolean deleteNumbers() {
        return true;
    }


    private boolean delNum(int ifFirstButton, int idSecondButton, ArrayList<Integer> numbersButtons) {
        if (ifFirstButton == idSecondButton) return false;
        if (ifFirstButton > idSecondButton) {//if aId > bId swap to make | first < second
            int cnt = ifFirstButton;
            ifFirstButton = idSecondButton;
            idSecondButton = cnt;
        }
        boolean flag = true;
        if (numbersButtons.get(ifFirstButton).equals(numbersButtons.get(idSecondButton)) || numbersButtons.get(ifFirstButton) + numbersButtons.get(idSecondButton) == 10) {
            if (ifFirstButton + 1 == idSecondButton || ifFirstButton + 9 == idSecondButton)
                return true;
            for (int i = 1; ifFirstButton + i < idSecondButton; i++) {
                if (numbersButtons.get(ifFirstButton + i) != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) return flag;
            flag = true;
            if ((idSecondButton - ifFirstButton) % 9 != 0) return false;
            for (int i = 9; ifFirstButton + i < idSecondButton; i += 9) {
                if (numbersButtons.get(ifFirstButton + i) != 0) flag = false;
            }
            return flag;
        } else return false;
    }

}
