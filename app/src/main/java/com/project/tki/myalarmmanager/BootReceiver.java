package com.project.tki.myalarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Deviation on 2018-09-16.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, MyService.class);
        context.startService(intent1);


    }
}
