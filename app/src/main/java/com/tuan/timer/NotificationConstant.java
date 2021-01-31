package com.tuan.timer;

public class NotificationConstant {
    public static final String ACTION_PAUSE= "com.tuan.timer.ACTION_PAUSE"; // must be included in manifest
    public static final String ACTION_RESUME= "com.tuan.timer.ACTION_RESUME";// must be included in manifest
    public static final String ACTION_STOP= "com.tuan.timer.ACTION_STOP";// must be included in manifest
    public static final String EXTRA_USER_NAME = "user_name";
    public static final long totalTime=1_000_000_000_000L;
    public static final long countDownInterval =1000;
}
//<receiver android:name=".NotificationReceiver2">
//<intent-filter>
//<action android:name="com.tuan.timer.ACTION_PAUSE" />
//<action android:name="com.tuan.timer.ACTION_RESUME" />
//<action android:name="com.tuan.timer.ACTION_STOP" />
//</intent-filter>
//</receiver>