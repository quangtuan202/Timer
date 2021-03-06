package com.tuan.timer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)

public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao UserDao();
    private static volatile UserDatabase INSTANCE;
    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "Sample.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}