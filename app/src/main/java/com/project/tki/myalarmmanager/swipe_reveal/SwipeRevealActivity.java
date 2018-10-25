package com.project.tki.myalarmmanager.swipe_reveal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.tki.myalarmmanager.R;
import com.project.tki.myalarmmanager.databinding.ActivitySwipeRevealBinding;

import java.util.ArrayList;
import java.util.List;

public class SwipeRevealActivity extends AppCompatActivity {

    private ActivitySwipeRevealBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_swipe_reveal);


        MainListAdapter mainListAdapter = new MainListAdapter(getMealList());
        b.rv.setAdapter(mainListAdapter);
    }

    public List<String> getMealList() {
        List<String> mealList = new ArrayList<>();
        mealList.add("Green Thai Curry");
        mealList.add("Granola");
        mealList.add("Poached Eggs");
        mealList.add("Spaghetti");
        mealList.add("Apple Pie");
        mealList.add("Grilled Cheese Sandwich");
        mealList.add("Vegetable Soup");
        mealList.add("Chicken Noodles");
        mealList.add("Fajitas");
        mealList.add("Chicken Pot Pie");
        mealList.add("Pasta and cauliflower casserole with chicken");
        mealList.add("Vegetable stir-fry");
        mealList.add("Sweet potato and orange soup");
        mealList.add("Vegetable Broth");
        return mealList;
    }
}
