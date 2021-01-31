package com.tuan.timer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="user")
public class User {
        @PrimaryKey(autoGenerate = true)
        public int id;
        @ColumnInfo(name = "Name")
        public String name;

        public User(String name){
                this.name=name;
        }
}
