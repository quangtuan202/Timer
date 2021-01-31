package com.tuan.timer;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;


import androidx.room.Dao;

@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    void insertPerson(User user);
    @Insert(onConflict = IGNORE)
    void insertOrReplacePerson(User user);
    @Update(onConflict = REPLACE)
    void updatePerson(User user);
    @Query("DELETE FROM user")
    void deleteAll();
    @Query("SELECT * FROM user WHERE Name= :name")
    public List<User> findAllUserByName(String name);
    @Query("SELECT * FROM user")
    public List<User> findAllUser();

}