package com.project.tki.myalarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, AlarmService.class);
        intent1.putExtra(AlarmService.ONOFF, true);
        context.startService(intent1);

    }
}
