package com.psa.hustlex.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.psa.hustlex.auth.UsernamePassword;

@Dao
public interface RoomDAO {

    @Insert
    void Insert(UsernamePassword... usernamePasswords);

    @Update
    void Update(UsernamePassword... usernamePasswords);

    @Delete
    void Delete(UsernamePassword usernamePassword);

    @Query("Select * from login where username = :username")
    UsernamePassword getUserWithUsername(String username);

    @Query("Select * from login where isloggedIn = 1")
    UsernamePassword getLoggedInUser();


//    @Insert
//    public void Insert(Reminders... reminders);
//
//    @Update
//    public void Update(Reminders... reminders);
//
//    @Delete
//    public void Delete(Reminders reminders);

//    @Query("Select * from reminder order by remindDate")
//    public List<Reminders> orderThetable();
//
//    @Query("Select * from reminder Limit 1")
//    public Reminders getRecentEnteredData();
//
//    @Query("Select * from reminder")
//    public List<Reminders> getAll();

}
