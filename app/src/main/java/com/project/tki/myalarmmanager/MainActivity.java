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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.project.tki.myalarmmanager.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String INTENT_ALARM = "com.project.tki.myalarmmanager.ALARM_START";
    private static final int REQUEST_CODE = 1234;

    private ActivityMainBinding b;
    private StringBuffer stringBuffer;
    long delay = 2000;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long time = System.currentTimeMillis();
            Date date = new Date(time);

            b.tvText.setText(appendStrBuffer(date.toString()));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        b.setActivity(this);

        stringBuffer = new StringBuffer("---- Status List ----");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotiManager.createChannel(getApplicationContext());
        }


        b.swTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    b.tvText.setText(appendStrBuffer(buttonView.getText().toString() + " repeat"));
                    onClickTimerRepeat(buttonView);
                } else {
                    b.tvText.setText(appendStrBuffer(buttonView.getText().toString() + " cancel"));
                    onClickTimerCancel(buttonView);
                }
            }
        });

        b.swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b.tvText.setText(appendStrBuffer(buttonView.getText().toString() + "_repeat"));
                    onClickAlarmRepeat(buttonView);
                } else {
                    b.tvText.setText(appendStrBuffer(buttonView.getText().toString() + "_cancel"));
                    onClickReleaseAlarm(buttonView);
                }
            }
        });


    }

    private StringBuffer appendStrBuffer(String text) {
        stringBuffer.append("\n");
        stringBuffer.append(text);
        return stringBuffer;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(AlarmReceiver.ALARM_FILTER);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    public void onClick(View view) {
        b.tv.setText(((Button) view).getText().toString());
        b.tvText.setText(appendStrBuffer(((Button) view).getText().toString()));

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

            case R.id.btn_timer:
                onClickTimer(view);
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

        /**       ---- activity 호출  ----     */
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

        long repeatDelay = Long.parseLong(b.etDelay.getText().toString());

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
                System.currentTimeMillis() + repeatDelay,
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

    private Timer mTimer;

    public void onClickTimer(View view) {

        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        };

        mTimer = new Timer();

        mTimer.schedule(mTask, 5000);

    }

    public void onClickTimerRepeat(View view) {

        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        };

        mTimer = new Timer();

        mTimer.schedule(mTask, 3000, 5000);  //3초 후에 Task를 실행하고 5초마다 반복 해라.

    }

    public void onClickTimerCancel(View view) {
        mTimer.cancel();
    }


    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }
}
