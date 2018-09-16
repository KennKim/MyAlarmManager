package com.project.tki.myalarmmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.StringDef;
import android.support.v4.app.NotificationCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Deviation on 2018-09-16.
 */

public class NotiManager {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            Channel.DEFAULT,
            Channel.CHAT
    })
    public @interface Channel {
        String DEFAULT = "default";
        String CHAT = "signin";
    }

    public static void createChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannelGroup group = new NotificationChannelGroup("group_id", "group_name");
            manager.createNotificationChannelGroup(group);

            NotificationChannel channel = new NotificationChannel(
                    Channel.DEFAULT,
                    "name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("description");
            channel.setGroup("group_id");
            channel.setLightColor(Color.MAGENTA);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.setVibrationPattern(new long[]{100, 1000, 1000, 100, 100, 100, 100, 100});
            manager.createNotificationChannel(channel);

        }

    }

    private static NotificationManager getManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void sendNotification(Context context, int id, @Channel String channel, String title, String body) {

        int icon = android.R.drawable.stat_notify_chat;
        Intent intent1 = new Intent("com.project.tki.myalarmmanager.TEST");
        intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                1234,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT
        );


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder builder = new Notification.Builder(context, channel)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(icon)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);  // 알림 터치시 반응 후 삭제
            getManager(context).notify(id, builder.build());
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channel);
            Notification notification = mBuilder
                    .setAutoCancel(true) // 알림 터치시 반응 후 삭제
                    .setSmallIcon(icon)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                    .setTicker(title)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setWhen(System.currentTimeMillis())
                    .setLights(Color.RED, 3000, 3000)
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/default_ios_sms"))
                    .setVibrate(new long[]{100, 1000, 1000, 100, 100, 100, 100, 100})
                    .build();
            getManager(context).notify(id, notification);
        }
    }

}
