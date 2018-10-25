package com.project.tki.myalarmmanager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.tki.myalarmmanager.databinding.ActivityStartBinding;
import com.project.tki.myalarmmanager.swipe_reveal.SwipeRevealActivity;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_start);
        b.setActivity(this);

    }


    public void onClickAlarm(View v){
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);

    }
    public void onClickSwipeReveal(View v){
        Intent intent = new Intent(StartActivity.this, SwipeRevealActivity.class);
        startActivity(intent);
    }
}
