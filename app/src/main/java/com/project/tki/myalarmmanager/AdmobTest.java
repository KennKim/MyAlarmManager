package com.project.tki.myalarmmanager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.project.tki.myalarmmanager.databinding.ActivityAdmobTestBinding;

public class AdmobTest extends AppCompatActivity {
    private ActivityAdmobTestBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_admob_test);
        b.setActivity(this);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

    }
}
