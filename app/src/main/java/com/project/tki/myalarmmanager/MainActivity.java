package com.project.tki.myalarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.tki.myalarmmanager.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private BroadcastReceiver mReceiver;
    private static final String INTENT_ALARM = "com.project.tki.myalarmmanager.ALARM_START";
    private static final int REQUEST_CODE = 1234;

    long delay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        b.setActivity(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotiManager.createChannel(getApplicationContext());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter(INTENT_ALARM);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    public void onClick(View view) {
        b.tv.setText(((Button) view).getText().toString());

        switch (view.getId()) {
            case R.id.btn_play:
                onClickOn(view);
                break;

            case R.id.btn_stop:
                onClickOff(view);
                break;

            case R.id.btn_Alarm:
                onClickAlarm(view);
                break;

            case R.id.btn_Repeat:
                onClickAlarmRepeat(view);
                break;

            case R.id.btn_Release:
                onClickReleaseAlarm(view);
                break;


        }

    }

    public void onClickOn(View view) {
        Intent intent = new Intent(MainActivity.this, AlarmService.class);
        intent.putExtra(AlarmService.ONOFF, true);
        startService(intent);
    }

    public void onClickOff(View view) {
        Intent intent = new Intent(MainActivity.this, AlarmService.class);
        intent.putExtra(AlarmService.ONOFF, false);
        startService(intent);
    }

    public void onClickAlarm(View view) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 39);
        calendar.set(Calendar.SECOND, 1);

        /**         * activity 호출         */
/*
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );*/

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i("ttttttttest", "alarm");
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + delay,
                    pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + delay,
                    pendingIntent
            );
        } else {
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + delay,
                    pendingIntent
            );
        }

        Toast.makeText(this, "set ok : " + calendar.getTimeInMillis(), Toast.LENGTH_LONG).show();


    }

    public void onClickAlarmRepeat(View view) {

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + delay,
                delay,
                pendingIntent
        );

        Toast.makeText(this, "set ok : ", Toast.LENGTH_LONG).show();


    }


    public void onClickReleaseAlarm(View view) {

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        manager.cancel(pendingIntent);

    }


}
