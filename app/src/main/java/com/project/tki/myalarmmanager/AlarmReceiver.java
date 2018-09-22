package com.project.tki.myalarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ALARM_FILTER = "ALARM_FILTER";

    /**
     * ----- AlarmManager 작동 순서 -----
     *
     * 1.MainActivity : AlarmManager에 AlarmReceiver를 넣은 PendingIntent를 넣어 알람 실행.
     * 2.정해놓은 시간에 BroadcastReceiver 발동.
     * 3.AlarmReceiver.onReceiver 호출.
     * 4.AlarmReceiver.onReceiver : 로직 실행하고, 다시 sendBroadcast 실행하면
     * 5.MainActivity : registerReceiver를 하고있는 Receiver.onReceiver 호출.
     *
     *
     * #. 반복은 onReceive에 다시 alarm set을 하는것이 나아 보임.
     *
     *
     */

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "AlarmReceiver: "  , Toast.LENGTH_LONG).show();


        Intent intent1 = new Intent(context, AlarmService.class);
        intent1.putExtra(AlarmService.ONOFF, true);
        context.startService(intent1);

        Intent intent2 = new Intent(ALARM_FILTER);
        context.sendBroadcast(intent2);

    }


}
