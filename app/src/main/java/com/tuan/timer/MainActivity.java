package com.tuan.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final long totalTime=1_000_000_000_000L;
    public final long countDownInterval =1000;
    public long timePass1=0;
    public long timePass2=0;
    CountDownTimer timer1;
    CountDownTimer timer2;
    SharedPreferences sharedPreference1 ;
    SharedPreferences sharedPreference2 ;
    public static final String MY_TAG = "Destroy";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //Get timer from SharedPreferences
        sharedPreference1 = getSharedPreferences("Timer1", MODE_PRIVATE);
        sharedPreference2 = getSharedPreferences("Timer2", MODE_PRIVATE);
        timePass1=sharedPreference1.getLong("Timer1",0);
        Log.d("Timer1:", String.valueOf(timePass1));
        timePass2=sharedPreference2.getLong("Timer2",0);
        Log.d("Timer2:", String.valueOf(timePass2));


            timer1 = new CountDownTimer(totalTime, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                        timePass1++;
                        //txtTimeRemain.setText("seconds remaining: " + millisUntilFinished / countDownInterval);
                        txtTimePass1.setText("seconds pass: " + timePass1);

                        //txtTotalTimeVariable.setText("Total time = "+totalTime);
                        //txtTimePassVariable.setText("Time passed = "+timePass);
                        //txtTimeRemainVariable.setText("Time Remains = "+count);
                    }

                @Override
                public void onFinish() {
                    //txtTimeRemain.setText("done!");
                }
            };
        // Timer 2
        timer2 = new CountDownTimer(totalTime, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timePass2++;
                //txtTimeRemain.setText("seconds remaining: " + millisUntilFinished / countDownInterval);
                txtTimePass2.setText("seconds pass: " + timePass2);

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
                timer1.start();

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