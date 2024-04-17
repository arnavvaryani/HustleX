package com.psa.hustlex.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.psa.hustlex.helpers.DateTypeConverter;
import com.psa.hustlex.models.Reminders;
import com.psa.hustlex.models.auth;


@Database(entities = {auth.class, Reminders.class},version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE = null;


    public abstract RoomDAO getRoomDAO();

    public static AppDatabase geAppdatabase(Context context){

        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"users")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;

    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

}
