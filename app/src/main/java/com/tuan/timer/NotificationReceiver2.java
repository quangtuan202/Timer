package com.tuan.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import static com.tuan.timer.MainActivity.currentUser;
import static com.tuan.timer.MainActivity.timer;


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
    public void timerResume(){
        MainActivity.timerMap.get(userName).countDownTimer.start();
        Log.d("Test resume:",userName);

    }

    public void timerCancel(){
        MainActivity.timerMap.get(userName).countDownTimer.cancel();
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
