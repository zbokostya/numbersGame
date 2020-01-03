package com.zbokostya.numgames.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zbokostya.numgames.GameActivity;
import com.zbokostya.numgames.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumbersViewHolder> {
    private List<Button> buttonsArrayList = new ArrayList<>();
    private GameActivity gameActivity;

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

    public void addItems(Collection<Button> button) {
        buttonsArrayList.addAll(button);
        notifyDataSetChanged();
    }


    public void removeItem(int idDeleteButton) {
        buttonsArrayList.remove(idDeleteButton);
    }

    public void removeListItem(Collection<Integer> listDeleteButtons) {
        buttonsArrayList.removeAll(listDeleteButtons);
    }


    class NumbersViewHolder extends RecyclerView.ViewHolder {
        private Button addedButton;

        public NumbersViewHolder(View itemView) {
            super(itemView);
            addedButton = itemView.findViewById(R.id.Button);
        }

        public void bind(Button button) {
            addedButton.setHeight(button.getHeight());
            //addedButton.setText(button.getText());
            addedButton.setId(button.getId());
            addedButton.setOnClickListener(oclBtn);
        }
    }

    View.OnClickListener oclBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gameActivity.listener(v.getId());
        }
    };

}
