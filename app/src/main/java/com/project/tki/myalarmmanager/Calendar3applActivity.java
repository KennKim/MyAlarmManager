package com.project.tki.myalarmmanager;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.tki.myalarmmanager.databinding.ActivityCalendar3Binding;

public class Calendar3applActivity extends AppCompatActivity {

    private ActivityCalendar3Binding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_calendar3);
        b.setActivity(this);







    }
}
