package com.leenalhamdan.timer.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.leenalhamdan.timer.model.dao.MotivationalQuoteDao;
import com.leenalhamdan.timer.model.dao.TimerDao;
import com.leenalhamdan.timer.model.entity.MotivationalQuote;
import com.leenalhamdan.timer.model.entity.Timer;

@Database(entities = {Timer.class,
        MotivationalQuote.class},
        version = 1,
        exportSchema = false)
public abstract class RoomDataBaseTimer extends RoomDatabase
{
    private static volatile RoomDataBaseTimer instance;

    public static RoomDataBaseTimer getDataBase(final Context context)
    {
        if (instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDataBaseTimer.class,"TimerDb")
                    .addCallback(callbackInitDb)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    private static Callback callbackInitDb=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onOpen(db);
        }
    };

    public abstract TimerDao getTimerDao();
    public abstract MotivationalQuoteDao getMotivationalQuoteDao();

}

