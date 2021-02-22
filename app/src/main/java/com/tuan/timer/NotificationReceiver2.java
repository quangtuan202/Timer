package com.tuan.timer;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class NotificationReceiver2 extends BroadcastReceiver {
    String userName;
    String action;
    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        Log.d("Action:",intent.getAction());
        userName = intent.getStringExtra(NotificationConstant.EXTRA_USER_NAME);
        if (action.equals(NotificationConstant.ACTION_PAUSE)) {
            timerCancel();

            Log.d("Username:", userName);
        }
        else if(action.equals(NotificationConstant.ACTION_RESUME)) {
            timerResume();
            Log.d("Action:", action);
        }


    }
    @SuppressLint("RestrictedApi")
    public void timerResume(){
        MainActivity.timerMap.get(userName).countDownTimer.start();
        //MainActivity.notificationManager.cancel(MainActivity.timerMap.get(userName).id);
        //MainActivity.timerMap.get(userName).notificationBuilder.clearActions();
        MainActivity.timerMap.get(userName).notificationBuilder.mActions.clear();
        MainActivity.timerMap.get(userName).notificationBuilder.setSmallIcon(R.drawable.ic_l)
                .setContentTitle(userName)
                //.addAction(R.drawable.ic_resume,"Play",timer.pendingIntentResume)
                .addAction(R.drawable.ic_stop,"Pause",MainActivity.timerMap.get(userName).pendingIntentPause)
                //.setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1))
                .setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)) //(0) only one action button
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setTicker("Ticker Text");
        MainActivity.notificationManager.notify(MainActivity.timerMap.get(userName).id, MainActivity.timerMap.get(userName).notificationBuilder.build());
        Log.d("Test resume:",userName);

    }

    @SuppressLint("RestrictedApi")
    public void timerCancel(){
        MainActivity.timerMap.get(userName).countDownTimer.cancel();
        //MainActivity.notificationManager.cancel(MainActivity.timerMap.get(userName).id);
        MainActivity.timerMap.get(userName).notificationBuilder.mActions.clear();
        MainActivity.timerMap.get(userName).notificationBuilder.setSmallIcon(R.drawable.ic_l)
                .setContentTitle(userName)
                .addAction(R.drawable.ic_resume,"Play",MainActivity.timerMap.get(userName).pendingIntentResume)
                //.addAction(R.drawable.ic_stop,"Pause",MainActivity.timerMap.get(userName).pendingIntentResume)
                //.setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1))
                .setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)) //(0) only one action button
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setTicker("Ticker Text");
        MainActivity.notificationManager.notify(MainActivity.timerMap.get(userName).id, MainActivity.timerMap.get(userName).notificationBuilder.build());
        Log.d("Test cancel:",userName);

    }
    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

}
