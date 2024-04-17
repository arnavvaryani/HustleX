package com.psa.hustlex.arnav.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.psa.hustlex.arnav.models.Reminders;
import com.psa.hustlex.arnav.models.auth;

@Dao
public interface RoomDAO {

    @Insert
    void Insert(auth... auths);

    @Update
    void Update(auth... auths);

    @Delete
    void Delete(auth auth);

    @Query("Select * from login where username = :username")
    auth getUserWithUsername(String username);

    @Query("Select * from login where isloggedIn = 1")
    auth getLoggedInUser();

    @Insert
    public void Insert(Reminders... reminders);

    @Update
    public void Update(Reminders... reminders);

    @Delete
    public void Delete(Reminders reminders);

}