package com.tuan.timer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Timer {
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


    public Timer (String userName, long timePass,int notificationId, Context context){
        this.userName= userName;
        this.sharedPreference=context.getSharedPreferences(userName, MODE_PRIVATE);
        this.timePass=timePass;
        this.notificationId=notificationId;
    }

    public Timer(String userName, int notificationId, Context context, Class notificationReceiver){
        this.context=context;
        this.userName= userName;
        this.sharedPreference=context.getSharedPreferences(userName, MODE_PRIVATE);
        this.notificationId=notificationId;
        this.timePass=0;
        //Intent
        this.intentPause=new Intent(context, notificationReceiver);
        this.intentPause.setAction(NotificationConstant.ACTION_PAUSE);
        this.intentPause.putExtra(NotificationConstant.EXTRA_USER_NAME,userName);
        Log.d("Put Extra:",NotificationConstant.ACTION_PAUSE);
        this.pendingIntentPause=PendingIntent.getBroadcast(this.context,notificationId,this.intentPause,PendingIntent.FLAG_UPDATE_CURRENT);
        //Intent
        this.intentResume=new Intent(context, notificationReceiver);
        this.intentResume.setAction(NotificationConstant.ACTION_RESUME);
        this.intentResume.putExtra(NotificationConstant.EXTRA_USER_NAME,userName);
        this.pendingIntentResume=PendingIntent.getBroadcast(this.context,notificationId+1000,this.intentResume,PendingIntent.FLAG_UPDATE_CURRENT);
        //Countdown Timer

        this.notificationBuilder= new NotificationCompat.Builder(this.context,userName );
    }

    public void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    this.userName,
                    "Channel"+this.userName,
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationChannel.setDescription("This is Channel");
            NotificationManager notificationManager = getSystemService(this.context,NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            //this.userName is channelId
        }
    }

}
