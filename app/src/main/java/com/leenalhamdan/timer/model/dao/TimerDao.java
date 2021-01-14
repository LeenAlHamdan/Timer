package com.leenalhamdan.timer.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.leenalhamdan.timer.model.entity.Timer;

@Dao
public interface TimerDao
{
    @Insert
    void insert(Timer timer);

    @Delete
    void delete(Timer timer);

    @Update
    void update(Timer timer);

    @Query("Select * From Timer LIMIT 1")
    LiveData<Timer> getTimer();

}
