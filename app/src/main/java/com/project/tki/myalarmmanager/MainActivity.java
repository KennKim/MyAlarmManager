package com.project.tki.myalarmmanager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.tki.myalarmmanager.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String INTENT_ALARM = "com.project.tki.myalarmmanager.ALARM_START";
    private static final String TAG = "ttttttest";
    private static final int REQUEST_CODE = 1234;

    private ActivityMainBinding b;
    private StringBuffer stringBuffer;
    long delay = 2000;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long time = System.currentTimeMillis();
            Date date = new Date(time);

            addStatus(date.toString());

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
                    addStatus(buttonView.getText().toString() + " repeat");
                    onClickTimerRepeat(buttonView);
                } else {
                    addStatus(buttonView.getText().toString() + " cancel");
                    onClickTimerCancel(buttonView);
                }
            }
        });

        b.swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addStatus(buttonView.getText().toString() + "_repeat");
                    onClickAlarmRepeat(buttonView);
                } else {
                    addStatus(buttonView.getText().toString() + "_cancel");
                    onClickReleaseAlarm(buttonView);
                }
            }
        });


    }

    private void addStatus(String text) {
        stringBuffer.append("\n");
        stringBuffer.append(text);
        b.tvText.setText(stringBuffer);
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
        addStatus(((Button) view).getText().toString());

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

            case R.id.btn_date:
                onClickDatePicker(view);
                break;

            case R.id.btn_date2:
                onClickDatePickerHolo(view);
                break;
            case R.id.btn_time:
                onClickTimePicker(view);
                break;

            case R.id.btn_time2:
                onClickTimePickerHolo(view);
                break;
            case R.id.btn_cal:
                onClickCalendar(view);
                break;
            case R.id.btn_cal2:
                onClickMaterialCalendar(view);
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
        Intent intent = new Intent(MainActivity.this,Calendar2proliActivity.class);

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
                Intent intent = new Intent(getApplicationContext(), Calendar2proliActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), Calendar2proliActivity.class);
                startActivity(intent);
            }
        };

        mTimer = new Timer();

        mTimer.schedule(mTask, 3000, 5000);  //3초 후에 Task를 실행하고 5초마다 반복 해라.

    }

    public void onClickTimerCancel(View view) {
        mTimer.cancel();
    }

    Calendar cal = Calendar.getInstance();

    public void onClickDatePicker(View view) {
        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        Log.e(TAG, cal.get(Calendar.YEAR) + "");
        Log.e(TAG, cal.get(Calendar.MONTH) + 1 + "");
        Log.e(TAG, cal.get(Calendar.DATE) + "");
        Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY) + "");
        Log.e(TAG, cal.get(Calendar.MINUTE) + "");

        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                String msg = String.format("%d 년 %d 월 %d 일", year, month + 1, date);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                addStatus(msg);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

        dialog.getDatePicker().setMaxDate(new Date().getTime());    //today까지만
        dialog.show();

    }

    public void onClickDatePickerHolo(View view) {

        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                String msg = String.format("%d 년 %d 월 %d 일", year, month + 1, date);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                addStatus(msg);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();

    }

    public void onClickTimePicker(View view) {
        TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                String msg = String.format("%d 시 %d 분", hour, min);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                addStatus(msg);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);

        dialog.show();

    }

    public void onClickTimePickerHolo(View view) {
        TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {


                String msg = String.format("%d 시 %d 분", hour, min);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                addStatus(msg);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);

        dialog.show();
    }

    private PopupWindow mPopupWindow;

    public void onClickCalendar(View v) {

        View view = getLayoutInflater().inflate(R.layout.layout_calendar, null);
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정


//         Set popup window animation style.
//        mPopupWindow.setAnimationStyle(R.style.animate_right);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();

//        Show popup window offset 1, 1 to smsBtton.
        mPopupWindow.showAsDropDown(v, 1, 1);
        mPopupWindow.showAtLocation(b.line1, Gravity.CENTER, 0, 0);

    }

    public void onClickMaterialCalendar(View v) {

        startActivity(new Intent(MainActivity.this,Calendar2proliActivity.class));

    }


    public void onClickAdmob(View v) {

        startActivity(new Intent(MainActivity.this,Calendar2proliActivity.class));

    }


    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }
}
