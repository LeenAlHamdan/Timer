package com.leenalhamdan.timer.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.leenalhamdan.timer.model.entity.MotivationalQuote;
import com.leenalhamdan.timer.model.entity.Timer;

@Dao
public interface MotivationalQuoteDao
{
    @Insert
    void insert(MotivationalQuote motivationalQuote);

    @Delete
    void delete(MotivationalQuote motivationalQuote);

    @Update
    void update(MotivationalQuote motivationalQuote);

    @Query("Select motivationalQuoteContent From MotivationalQuote LIMIT 1")
    LiveData<String> getMotivationalQuote();
}
