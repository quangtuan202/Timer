package com.tuan.timer;

public class ConvertTime {
    public static String convertToTime(Long time){
        String stringHours;
        String stringMinutes;
        String stringSeconds;

        int hours = (int) (time/3600);
        int minutes= (int) ((time-hours*3600)/60);
        int seconds= (int) (time-hours*3600-minutes*60);
        if(hours<10){
            stringHours="0"+String.valueOf(hours);
        }
        else{
            stringHours=String.valueOf(hours);
        }

        if(minutes<10){
            stringMinutes="0"+String.valueOf(minutes);
        }
        else{
            stringMinutes=String.valueOf(minutes);
        }
        if(seconds<10){
            stringSeconds="0"+String.valueOf(seconds);
        }
        else{
            stringSeconds=String.valueOf(seconds);
        }

        return(stringHours+":"+stringMinutes+":"+stringSeconds);

    }
}
