package com.zbokostya.numgames.GameLogic;

import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbokostya.numgames.R;
import com.zbokostya.numgames.enums.IntegerValues;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;


public class GameLogic {
    private static GameLogic instance;

    private int spanCount = IntegerValues.spanCount.getValue();

    private GameLogic() {
    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    //drawables
    private int setBackgroundCrossed = R.drawable.crossed128_128;
    private int BLACK = Color.BLACK;
    private int pressedColor = Color.CYAN;

    private int idFirstButton = -1;

    private int hintFirstButton = -1;
    private int hintSecondButton = -1;


    public boolean isGameEnded(ArrayList<Integer> intArrayList) {
        for (int el : intArrayList) {
            if (el != 0 && el != -1) return false;
        }
        return true;
    }


    public void setIdFirstButton(int n) {

        idFirstButton = n;
    }

    public void clearRows(ArrayList<TextView> buttonsList, ArrayList<Integer> intArrayList) {
        int size = intArrayList.size();
        boolean flag;
        for (int i = 0; i < size / spanCount; i++) {
            flag = true;
            for (int j = 0; j < spanCount; j++) {
                if (intArrayList.get(i * spanCount + j) != 0 && intArrayList.get(i * spanCount + j) != -1) {
                    flag = false;
                    break;
                }
            }
            //if already skipped
            if (buttonsList.get(i * spanCount).getLayoutParams().height == 0) flag = false;

            if (flag) {
                //changed params height to 0
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) buttonsList.get(i * spanCount).getLayoutParams();
                params.height = 0;
                params.bottomMargin = 0;
                for (int j = 0; j < spanCount; j++) {
                    TextView cnt = buttonsList.get(i * spanCount + j);
                    cnt.setLayoutParams(params);
                    buttonsList.set(i * spanCount + j, cnt);
//                    buttonsList.remove(i * spanCount + j);
                    intArrayList.set(i * spanCount + j, -1);//-1 not counting
                }
            }
        }
    }


    public boolean mainActivator(int idSecondButton, ArrayList<TextView> buttons, ArrayList<Integer> buttonsIds) {
        TextView firstButton;
        TextView secondButton;

        if (buttonsIds.get(idSecondButton) == 0) return false;
        if (idFirstButton == -1) {
            firstButton = buttons.get(idSecondButton);
            firstButton.setTextColor(ContextCompat.getColor(firstButton.getContext(), R.color.colorAccent));
            buttons.set(idSecondButton, firstButton);
            idFirstButton = idSecondButton;
            return false;
        }
        if (idFirstButton == idSecondButton) {
            firstButton = buttons.get(idFirstButton);
            firstButton.setTextColor(BLACK);
            buttons.set(idSecondButton, firstButton);
            idFirstButton = -1;
            return false;
        }
        firstButton = buttons.get(idFirstButton);
        secondButton = buttons.get(idSecondButton);

        firstButton.setTextColor(BLACK);
        secondButton.setTextColor(BLACK);

        if (ifNumbersCanDelete(firstButton.getId(), secondButton.getId(), buttonsIds)) {
//        if (true) {
            buttonsIds.set(idFirstButton, 0);
            buttonsIds.set(idSecondButton, 0);

            firstButton.setBackgroundResource(setBackgroundCrossed);
            secondButton.setBackgroundResource(setBackgroundCrossed);

            firstButton.setText(Integer.toString(idFirstButton));
            secondButton.setText(Integer.toString(idSecondButton));

            buttons.set(idFirstButton, firstButton);
            buttons.set(idSecondButton, secondButton);
            idFirstButton = -1;
            return true;
        }
        idFirstButton = -1;
        return false;

    }


    private boolean ifNumbersCanDelete(int idFirstButton, int idSecondButton, ArrayList<Integer> numbersButtons) {
        if (idFirstButton == idSecondButton) return false;

        if (idFirstButton > idSecondButton) {//if aId > bId swap to make | first < second
            int cnt = idFirstButton;
            idFirstButton = idSecondButton;
            idSecondButton = cnt;
        }

        boolean flag = true;
        //check if a + b == 10 || a == b
        if (numbersButtons.get(idFirstButton).equals(numbersButtons.get(idSecondButton))
                || numbersButtons.get(idFirstButton) + numbersButtons.get(idSecondButton) == 10) {

            //check if close to each other
            if (idFirstButton + 1 == idSecondButton || idFirstButton + 9 == idSecondButton)
                return true;
            //check if line between numbers is == 0
            for (int i = idFirstButton + 1; i < idSecondButton; i++) {
                if (numbersButtons.get(i) != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) return true;

            //check if not in 1 line vertical
            if ((idSecondButton - idFirstButton) % 9 != 0) return false;

            flag = true;
            //check if not in 1 line horizontal
            for (int i = idFirstButton + 9; i < idSecondButton; i += 9) {
                if (numbersButtons.get(i) != 0) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }
        return false;
    }

    private int[] hint(ArrayList<Integer> numbersButtons) {
        int[] rez = new int[2];
        hintLine(numbersButtons, rez);
        if (rez[1] != 0) return rez;
        hintVertical(numbersButtons, rez);
        Log.d("hint2", rez[0] + " " + rez[1]);
        return rez;
    }

    private void hintVertical(ArrayList<Integer> numbersButtons, int[] hint) {
        //fixme
        for (int i = 0; i < spanCount; i++) {
            hint[0] = i;
//            Log.d("hintval", hint[0] + "");
            for (int j = i; j < numbersButtons.size(); j += spanCount) {
                for (int k = j + spanCount; k < numbersButtons.size(); k += spanCount) {
                    if (numbersButtons.get(k) != 0 && numbersButtons.get(k) != -1) {
                        hint[0] = k;
                        break;
                    }
                }

//                Log.d("hintval1", hint[0] + "|" + numbersButtons.get(hint[0]));
                for (int k = hint[0] + spanCount; k < numbersButtons.size(); k += spanCount) {
//                    Log.d("hintval2", hint[0] + "|" + numbersButtons.get(hint[0]) + " " + k + "|" + numbersButtons.get(k));
                    if (numbersButtons.get(k) != 0 && numbersButtons.get(k) != -1) {
                        if (numbersButtons.get(hint[0]).equals(numbersButtons.get(k))
                                || numbersButtons.get(hint[0]) + numbersButtons.get(k) == 10) {
                            hint[1] = k;
                            return;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

    }

    private void hintLine(ArrayList<Integer> numbersButtons, int[] hint) {
        for (int i = 0; i < numbersButtons.size(); i++) {
            if (numbersButtons.get(i) != -1 && numbersButtons.get(i) != 0) {
                hint[0] = i;
            }

            for (int j = hint[0] + 1; j < numbersButtons.size(); j++) {
                if (numbersButtons.get(j) != 0 && numbersButtons.get(j) != -1) {
                    if (numbersButtons.get(hint[0]).equals(numbersButtons.get(j))
                            || numbersButtons.get(hint[0]) + numbersButtons.get(j) == 10) {
                        hint[1] = j;
                        return;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void hint(ArrayList<Integer> numbersButtons, ArrayList<TextView> buttonsList) {
        int[] arr = hint(numbersButtons);
//        Log.d("hint3", arr[0] + " " + arr[1]);
        //fixme
        // -change color back
        if (arr[1] != 0) {
            hintFirstButton = arr[0];
            hintSecondButton = arr[1];
            TextView button1 = buttonsList.get(arr[0]);
            button1.setTextColor(Color.RED);
            TextView button2 = buttonsList.get(arr[1]);
            button2.setTextColor(Color.RED);
            buttonsList.set(arr[0], button1);
            buttonsList.set(arr[1], button2);
        }
    }


    public static String info(ArrayList<Integer> intArrayList) {
        StringBuilder sb = new StringBuilder();
        int[] arr = new int[9];
        for (Integer integer : intArrayList) {
            if (!integer.equals(0) && !integer.equals(-1)) {
                arr[integer - 1]++;
            }
        }
        sb.append("one: ").append(arr[0]).append('\n');
        sb.append("two: ").append(arr[1]).append('\n');
        sb.append("three: ").append(arr[2]).append('\n');
        sb.append("four: ").append(arr[3]).append('\n');
        sb.append("five: ").append(arr[4]).append('\n');
        sb.append("six: ").append(arr[5]).append('\n');
        sb.append("seven: ").append(arr[6]).append('\n');
        sb.append("eight: ").append(arr[7]).append('\n');
        sb.append("nine: ").append(arr[8]);
        return sb.toString();
    }

}
