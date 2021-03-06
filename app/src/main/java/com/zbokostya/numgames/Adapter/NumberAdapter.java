package com.zbokostya.numgames.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zbokostya.numgames.GameLogic.GameLogic;
import com.zbokostya.numgames.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumbersViewHolder> {
    private ArrayList<Button> buttonsArrayList = new ArrayList<>();
    private ArrayList<Integer> intArrayList = new ArrayList<>();
    private GameLogic gameLogic = GameLogic.getInstance();

    @NonNull
    @Override
    public NumbersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buttons_layout, parent, false);
        return new NumbersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NumbersViewHolder holder, int position) {
        holder.bind(buttonsArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return buttonsArrayList.size();
    }

    public void addToIntArr(ArrayList<Integer> intArray) {
        intArrayList.addAll(intArray);
    }

    public void addItems(List<Button> buttons) {
        buttonsArrayList.addAll(buttons);
        notifyDataSetChanged();
    }

    public void setItems(Collection<Button> buttons) {
        buttonsArrayList.clear();
        buttonsArrayList.addAll(buttons);
        notifyDataSetChanged();
    }

    public void addItem(Button button) {
        buttonsArrayList.add(button);
        intArrayList.add(button.getId());
        notifyDataSetChanged();
    }

    public void removeItem(int idDeleteButton) {
        buttonsArrayList.remove(idDeleteButton);
        intArrayList.remove(idDeleteButton);
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        buttonsArrayList.clear();
        intArrayList.clear();
        gameLogic.setIdFirstButton(-1);
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getIntArrayList() {
        return intArrayList;
    }

    public ArrayList<Button> getButtonsArrayList() {
        return buttonsArrayList;
    }

    public void setIntArrayList(ArrayList<Integer> intList) {
        intArrayList.clear();
        intArrayList.addAll(intList);
        notifyDataSetChanged();
    }

    class NumbersViewHolder extends RecyclerView.ViewHolder {
        private Button elementButton;

        NumbersViewHolder(View itemView) {
            super(itemView);
            elementButton = itemView.findViewById(R.id.Button);
        }

        void bind(Button button) {
            elementButton.setLayoutParams(button.getLayoutParams());
            elementButton.setText(button.getText());
            elementButton.setTextColor(button.getTextColors());
            elementButton.setBackgroundDrawable(button.getBackground());
            elementButton.setId(button.getId());
            elementButton.setOnClickListener(oclBtn);
        }
    }

    private View.OnClickListener oclBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
           // Log.d("te", cnt + "");
            gameLogic.mainActivator(id, buttonsArrayList, intArrayList);
            notifyDataSetChanged();
            if (gameLogic.ifGameEnded(intArrayList)) {

            }
        }
    };

    public void clearRows() {
        gameLogic.clearRows(buttonsArrayList, intArrayList);
        notifyDataSetChanged();
    }


}
