package com.project.tki.myalarmmanager.swipe_reveal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tki.myalarmmanager.R;

import java.util.List;

/**
 * Created by Mark O'Sullivan on 25th February 2018.
 */

public class MainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> shoppingList;

    public MainListAdapter(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_main, parent, false);
        return new MainListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainListViewHolder h = (MainListViewHolder) holder;

        h.mealTV.setText(shoppingList.get(position));

        h.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "INFO CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        h.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "EDIT CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public static class MainListViewHolder extends RecyclerView.ViewHolder {

        protected TextView mealTV;
        protected ImageButton infoButton;
        protected ImageButton editButton;

        protected MainListViewHolder(View itemView) {
            super(itemView);
            mealTV = itemView.findViewById(R.id.meal_tv);
            infoButton = itemView.findViewById(R.id.info_button);
            editButton= itemView.findViewById(R.id.edit_button);
        }
    }
}
