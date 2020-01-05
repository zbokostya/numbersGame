package com.zbokostya.numgames.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zbokostya.numgames.GameActivity;
import com.zbokostya.numgames.GameLogic.GameLogic;
import com.zbokostya.numgames.R;

import java.util.ArrayList;
import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumbersViewHolder> {
    private ArrayList<Button> buttonsArrayList = new ArrayList<>();
    private ArrayList<Integer> intArrayList = new ArrayList<>();
    private GameLogic gameLogic;

    @NonNull
    @Override
    public NumbersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        gameLogic = GameLogic.getInstance();
        //gameLogic.initArrays(buttonsArrayList, intArrayList);
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


    public void addItems(Collection<Button> button) {
        buttonsArrayList.addAll(button);
        for (Button btn : button) {
            intArrayList.add(btn.getId());
        }
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
    }

    public void removeAllItems() {
        buttonsArrayList.clear();
        intArrayList.clear();
    }


    class NumbersViewHolder extends RecyclerView.ViewHolder {
        private Button addedButton;

        public NumbersViewHolder(View itemView) {
            super(itemView);
            addedButton = itemView.findViewById(R.id.Button);
        }

        public void bind(Button button) {
            // addedButton.setHeight(button.getHeight());
            addedButton.setText(button.getText());
            addedButton.setBackgroundColor(Color.CYAN);
            addedButton.setId(button.getId());
            addedButton.setOnClickListener(oclBtn);
        }
    }

    View.OnClickListener oclBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gameLogic.initArrays(buttonsArrayList, intArrayList);
            gameLogic.mainActivator(v.getId());
            Log.d("123", v.getId() + "");
            buttonsArrayList.clear();
            intArrayList.clear();
            buttonsArrayList = gameLogic.returnButtonArray();
            intArrayList = gameLogic.returnIntArray();
            notifyDataSetChanged();
        }
    };

}
