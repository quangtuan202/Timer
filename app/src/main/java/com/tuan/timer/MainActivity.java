package com.tuan.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.tuan.timer.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {
    public final long totalTime=1_000_000_000_000L;
    public final long countDownInterval =1000;
    public static long timePass1=0;
    public static long timePass2=0;
    public static CountDownTimer timer1;
    public static CountDownTimer timer2;
    public static SharedPreferences sharedPreference1 ;
    public static SharedPreferences sharedPreference2 ;
    public static final String MY_TAG = "Destroy";
    private NotificationManagerCompat notificationManager;
    private MediaSessionCompat mediaSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = NotificationManagerCompat.from(this);

        TextView txtTimePass1=findViewById(R.id.time_pass1);
        Button btn_start=findViewById(R.id.btn_start);
        Button btn_pause=findViewById(R.id.btn_pause);
        Button btn_stop=findViewById(R.id.btn_stop);
        Button btn_resume=findViewById(R.id.btn_resume);

        TextView txtTimePass2=findViewById(R.id.time_pass2);
        Button btn_start2=findViewById(R.id.btn_start2);
        Button btn_pause2=findViewById(R.id.btn_pause2);
        Button btn_stop2=findViewById(R.id.btn_stop2);
        Button btn_resume2=findViewById(R.id.btn_resume2);

        Button btn_intent=findViewById(R.id.btn_intent);
        Button btn_noti=findViewById(R.id.btn_noti);

        //NotificationManager notificationManager;

        //Get timer from SharedPreferences
        sharedPreference1 = getSharedPreferences("Timer1", MODE_PRIVATE);
        sharedPreference2 = getSharedPreferences("Timer2", MODE_PRIVATE);
        timePass1=sharedPreference1.getLong("Timer1",0);
        Log.d("Timer1:", String.valueOf(timePass1));
        timePass2=sharedPreference2.getLong("Timer2",0);
        Log.d("Timer2:", String.valueOf(timePass2));



        Intent notificationIntentPause = new Intent(MainActivity.this, NotificationReceiver2.class);
        notificationIntentPause.putExtra("action","0");
        PendingIntent pendingIntentPause = PendingIntent.getBroadcast(this,
                0, notificationIntentPause, PendingIntent.FLAG_UPDATE_CURRENT);

       Intent notificationIntentResume = new Intent(MainActivity.this, NotificationReceiver2.class);
       notificationIntentResume.putExtra("action","1");
        PendingIntent pendingIntentResume = PendingIntent.getBroadcast(this,
          1, notificationIntentResume, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "Toast check");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notification1 = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_hand)
                .setContentTitle("Time pass 1")
                //.setContentText(String.valueOf(System.currentTimeMillis()))
                .addAction(R.drawable.ic_play,"Play",pendingIntentResume)
                .addAction(R.drawable.ic_pause,"Pause",pendingIntentPause)
                //.addAction(R.drawable.ic_play,"Play",actionIntent)
                //.addAction(R.drawable.ic_pause,"Pause",actionIntent)
                .setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1))
                //.setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setTicker("Ticker Text");
                //.setWhen(System.currentTimeMillis())
                //.setShowWhen(true)// the time stamp, you will probably use System.currentTimeMillis() for most scenarios
                //.setUsesChronometer(true);




        timer1 = new CountDownTimer(totalTime, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                        timePass1++;
                        //txtTimeRemain.setText("seconds remaining: " + millisUntilFinished / countDownInterval);
                        txtTimePass1.setText("seconds pass: " + timePass1);
                        notification1.setContentText(ConvertTime.convertToTime(timePass1));
                        notificationManager.notify(1, notification1.build());

                        //txtTotalTimeVariable.setText("Total time = "+totalTime);
                        //txtTimePassVariable.setText("Time passed = "+timePass);
                        //txtTimeRemainVariable.setText("Time Remains = "+count);
                   // Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                     //       .setSmallIcon(R.drawable.ic_hand)
                        //    .setContentTitle("Time pass 1")
                          //  .setContentText(String.valueOf(timePass1))
                            //.addAction(R.drawable.ic_play,"Play",null)
                            //.addAction(R.drawable.ic_pause,"Pause",null)
                            //.setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1).setMediaSession(mediaSession.getSessionToken()))
                            //.setPriority(NotificationCompat.PRIORITY_HIGH)
                            //.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            //.build();
                   // notificationManager.notify(1, notification);
                    }

                @Override
                public void onFinish() {
                    notification1.setContentText("Done");
                }
            };
        // Timer 2
        timer2 = new CountDownTimer(totalTime, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timePass2++;
                //txtTimeRemain.setText("seconds remaining: " + millisUntilFinished / countDownInterval);
                txtTimePass2.setText("seconds pass: " + timePass2);
               // Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                 //       .setSmallIcon(R.drawable.ic_hand)
                   //     .setContentTitle("Time pass 2")
                   //     .setContentText(String.valueOf(timePass2))
                   //     .addAction(R.drawable.ic_play,"Play",null)
                   //     .addAction(R.drawable.ic_pause,"Pause",null)
                   //     .setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1).setMediaSession(mediaSession.getSessionToken()))
                   //     .setPriority(NotificationCompat.PRIORITY_HIGH)
                   //     .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    //    .build();
                //notificationManager.notify(2, notification);

                //txtTotalTimeVariable.setText("Total time = "+totalTime);
                //txtTimePassVariable.setText("Time passed = "+timePass);
                //txtTimeRemainVariable.setText("Time Remains = "+count);
            }

            @Override
            public void onFinish() {
                //txtTimeRemain.setText("done!");
            }
        };

        //If timer1 and timer2 >0
        if(timePass1>0 && timePass2>0){
            timer1.start();
            timer2.start();
        }
        else if (timePass1>0){
            timer1.start();
        }
        else if (timePass2>0){
            timer2.start();
        }
        else{
            ;
        }



        // Start counting
        btn_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(timePass1==0) {
                    timer1.start();
                    notificationManager.notify(1, notification1.build());
                }
                else{
                    ;
                }


            }

        });

        // Pause
        btn_pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer1.cancel();
            }

        });

        // Resume
        btn_resume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer1.start();
            }

        });

        //Stop
        btn_stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer1.cancel();
                timePass1=0;
                txtTimePass1.setText("seconds pass: " + timePass1);
                sharedPreference1 = getSharedPreferences("Timer1", MODE_PRIVATE);
                //sharedPreference2 = getSharedPreferences("Timer2", MODE_PRIVATE);
                SharedPreferences.Editor myEdit1 = sharedPreference1.edit();
                //SharedPreferences.Editor myEdit2 = sharedPreference2.edit();

// Storing the key and its value
                myEdit1.putLong("Timer1", timePass1);
                Log.i(MY_TAG, String.valueOf(timePass1));
               // myEdit2.putLong("Timer2",  timePass2);
                Log.i(MY_TAG, String.valueOf(timePass2));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit1.apply();
                //myEdit2.apply();

            }

        });

        ///////////////////////////////////////////////////



        // Start counting
        btn_start2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer2.start();
            }

        });

        // Pause
        btn_pause2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer2.cancel();
            }

        });

        // Resume
        btn_resume2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer2.start();
            }

        });

        //Stop
        btn_stop2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timer2.cancel();
                timePass2=0;
                txtTimePass2.setText("seconds pass: " + timePass2);
                //sharedPreference1 = getSharedPreferences("Timer1", MODE_PRIVATE);
                sharedPreference2 = getSharedPreferences("Timer2", MODE_PRIVATE);
                //SharedPreferences.Editor myEdit1 = sharedPreference1.edit();
                SharedPreferences.Editor myEdit2 = sharedPreference2.edit();

// Storing the key and its value
                //myEdit1.putLong("Timer1", timePass1);
                //Log.i(MY_TAG, String.valueOf(timePass1));
                myEdit2.putLong("Timer2",  timePass2);
                Log.i(MY_TAG, String.valueOf(timePass2));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                //myEdit1.apply();
                myEdit2.apply();

            }

        });

        btn_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });

        btn_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_hand)
                        .setContentTitle("Time pass")
                        .setContentText(String.valueOf(timePass1))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                notificationManager.notify(1, notification);


            }
        });




    }
    @Override
    public void onPause() {
        super.onPause();
        sharedPreference1 = getSharedPreferences("Timer1", MODE_PRIVATE);
        sharedPreference2 = getSharedPreferences("Timer2", MODE_PRIVATE);
        SharedPreferences.Editor myEdit1 = sharedPreference1.edit();
        SharedPreferences.Editor myEdit2 = sharedPreference2.edit();

// Storing the key and its value
        myEdit1.putLong("Timer1", timePass1);
        Log.i(MY_TAG, String.valueOf(timePass1));
        myEdit2.putLong("Timer2",  timePass2);
        Log.i(MY_TAG, String.valueOf(timePass2));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit1.apply();
        myEdit2.apply();
        Log.i(MY_TAG, "onPause");
    }



}