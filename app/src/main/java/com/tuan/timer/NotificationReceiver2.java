package com.tuan.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getStringExtra("action");
        if(action.equals("1")){
            timerResume();
            Log.d("Action:", action);
        }
        else if(action.equals("0")){
            timerCancel();
            Log.d("Action:", action);

        }

    }
    public void timerResume(){
        MainActivity.timer1.start();
        Log.d("Test resume:","Resume");

    }

    public void timerCancel(){
        MainActivity.timer1.cancel();
        Log.d("Test cancel:","Cancel");

    }

}
