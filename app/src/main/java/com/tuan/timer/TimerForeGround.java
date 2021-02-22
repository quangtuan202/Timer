package com.tuan.timer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static androidx.core.content.ContextCompat.getSystemService;

public class TimerForeGround extends Service {

    public String userName;
    public int id;
    public SharedPreferences sharedPreference;
    public CountDownTimer countDownTimer;
    public long timePass;
    public NotificationCompat.Builder notificationBuilder;
    public int  notificationId;
    public Intent intentPause;
    public PendingIntent pendingIntentPause;
    public Intent intentResume;
    public PendingIntent pendingIntentResume;
    public Context context;

    @Override
    public void onCreate(){
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
