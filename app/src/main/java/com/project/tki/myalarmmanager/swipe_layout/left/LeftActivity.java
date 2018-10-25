package com.project.tki.myalarmmanager.swipe_layout.left;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.tki.myalarmmanager.R;

import java.util.ArrayList;
import java.util.List;


public class LeftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lleft);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new LeftAdapter(getItems()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            items.add("item " + i);
        }
        return items;
    }
}
