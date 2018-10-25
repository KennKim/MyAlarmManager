package com.project.tki.myalarmmanager.swipe_layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.tki.myalarmmanager.R;
import com.project.tki.myalarmmanager.swipe_layout.horizontal.HorizontalActivity;
import com.project.tki.myalarmmanager.swipe_layout.left.LeftActivity;
import com.project.tki.myalarmmanager.swipe_layout.right.RightActivity;

public class SwipeLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_layout);


        findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LeftActivity.class));
            }
        });

        findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RightActivity.class));
            }
        });

        findViewById(R.id.horizontal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), HorizontalActivity.class));
            }
        });

    }




}
