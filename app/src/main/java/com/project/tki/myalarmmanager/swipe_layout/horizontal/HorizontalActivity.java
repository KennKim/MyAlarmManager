package com.project.tki.myalarmmanager.swipe_layout.horizontal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.tki.myalarmmanager.R;

import java.util.ArrayList;
import java.util.List;


public class HorizontalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new HorizontalAdapter(getItems()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            items.add("item " + i);
        }
        return items;
    }
}
