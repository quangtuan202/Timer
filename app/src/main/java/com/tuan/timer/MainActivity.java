package com.tuan.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.tuan.timer.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public final long totalTime=1_000_000_000_000L;
    public final long countDownInterval =1000;
    public static long timePass1=0;
    public static long timePass2=0;
    public static CountDownTimer timer1;
    public static CountDownTimer timer2;
    public static SharedPreferences sharedPreference1 ;
    public static SharedPreferences sharedPreference2 ;
    public static String currentUser;
    //public static Timer timer;

    public static final String MY_TAG = "Destroy";
    public static NotificationManagerCompat notificationManager;
    private MediaSessionCompat mediaSession;
    UserDatabase userDatabase;
    Intent notificationIntentPause;
    Intent notificationIntentResume;
    PendingIntent pendingIntentPause;
    PendingIntent pendingIntentResume;
    TextView txtTimePass1;
    int uniqueId;
    List<Timer> timerList=new ArrayList();
    public static HashMap<String,Timer> timerMap = new HashMap<String, Timer>();
    List<String> nameListForCheckExistence=new ArrayList();
    public static int requestCode=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = NotificationManagerCompat.from(this);

        txtTimePass1=findViewById(R.id.time_pass1);
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
        Button btn_add_user=findViewById(R.id.btn_add_user);
        Spinner spinner=findViewById(R.id.spinner);

        userDatabase=Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"infodb").allowMainThreadQueries().build();

        List<User> userList = userDatabase.UserDao().findAllUser();
        List<String> nameList=new ArrayList<String>();
        for (int i=0;i<userList.size();i++){
            nameList.add(userList.get(i).name);
            Log.d("User name:",userList.get(i).name);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);


        //NotificationManager notificationManager;

        //Get timer from SharedPreferences
        sharedPreference1 = getSharedPreferences("Timer1", MODE_PRIVATE);
        sharedPreference2 = getSharedPreferences("Timer2", MODE_PRIVATE);
        timePass1=sharedPreference1.getLong("Timer1",0);
        Log.d("Timer1:", String.valueOf(timePass1));
        timePass2=sharedPreference2.getLong("Timer2",0);
        Log.d("Timer2:", String.valueOf(timePass2));



        notificationIntentPause = new Intent(MainActivity.this, NotificationReceiver2.class);
        notificationIntentPause.putExtra("action","0");
        pendingIntentPause = PendingIntent.getBroadcast(this,
                0, notificationIntentPause, PendingIntent.FLAG_UPDATE_CURRENT);

       notificationIntentResume = new Intent(MainActivity.this, NotificationReceiver2.class);
       notificationIntentResume.putExtra("action","1");
       pendingIntentResume = PendingIntent.getBroadcast(this,
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

                if(timerMap.get(currentUser).timePass==0) {
                    //startForeground(timer.id, timer.notificationBuilder.build());
                    timerMap.get(currentUser).countDownTimer.start();
                    //Log.d("Action set:",timerMap.get(currentUser).intentPause.getAction());
                    //notificationManager.notify(1, notification1.build());
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

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user1=new User("abc");
                User user2=new User("def");
                User user3=new User("ghi");
                userDatabase.UserDao().insertPerson(user1);
                userDatabase.UserDao().insertPerson(user2);
                userDatabase.UserDao().insertPerson(user3);


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

//ASpinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        uniqueId=position;
        currentUser = parent.getItemAtPosition(position).toString();
        String userName= parent.getItemAtPosition(position).toString();
        Log.d("Position:", String.valueOf(position));
        //Use position as notificationId
        Timer timer=new Timer(userName,position,MainActivity.this,NotificationReceiver2.class);
        timer.sharedPreference=getSharedPreferences(timer.userName, MODE_PRIVATE);
        timer.timePass=timer.sharedPreference.getLong(timer.userName,0);
        timer.id=position;

        //Create notification builder
        timer.createNotificationChannels(MainActivity.this);
        timer.notificationBuilder.setSmallIcon(R.drawable.ic_l)
                .setContentTitle(userName)
                //.addAction(R.drawable.ic_resume,"Play",timer.pendingIntentResume)
                .addAction(R.drawable.ic_stop,"Pause",timer.pendingIntentPause)
                //.setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1))
                .setStyle(new  androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setTicker("Ticker Text");


        timer.countDownTimer = new CountDownTimer(totalTime, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.timePass++;
                //txtTimeRemain.setText("seconds remaining: " + millisUntilFinished / countDownInterval);
                txtTimePass1.setText("seconds pass: " + timer.timePass);
                timer.notificationBuilder.setContentText(ConvertTime.convertToTime(timer.timePass));
                notificationManager.notify(timer.id, timer.notificationBuilder.build());
                //startForeground(timer.id, timer.notificationBuilder.build());

            };


            @Override
            public void onFinish() {
                ;
            }
        };

        //Add name to list

        if(!nameListForCheckExistence.contains(timer.userName)) {
            nameListForCheckExistence.add(timer.userName);
            timerMap.put(timer.userName,timer);
        }
        else{
            ;
        }
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: "+String.valueOf(position) + timer.userName, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}