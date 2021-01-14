package com.leenalhamdan.timer.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Timer")
public class Timer
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;

    @ColumnInfo(name = "NumberOfTrainingIntervals")
    private long numberOfTrainingIntervals;

    @ColumnInfo(name = "DurationOfTrainingIntervals")
    private long durationOfTrainingIntervals;

    @ColumnInfo(name = "DurationOfBreakIntervals")
    private long durationOfBreakIntervals;

    public Timer()
    {
    }

    public Timer(long numberOfTrainingIntervals, long durationOfTrainingIntervals, long durationOfBreakIntervals)
    {
        this.numberOfTrainingIntervals = numberOfTrainingIntervals;
        this.durationOfTrainingIntervals = durationOfTrainingIntervals;
        this.durationOfBreakIntervals = durationOfBreakIntervals;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberOfTrainingIntervals() {
        return numberOfTrainingIntervals;
    }

    public void setNumberOfTrainingIntervals(long numberOfTrainingIntervals) {
        this.numberOfTrainingIntervals = numberOfTrainingIntervals;
    }

    public long getDurationOfTrainingIntervals() {
        return durationOfTrainingIntervals;
    }

    public void setDurationOfTrainingIntervals(long durationOfTrainingIntervals) {
        this.durationOfTrainingIntervals = durationOfTrainingIntervals;
    }

    public long getDurationOfBreakIntervals() {
        return durationOfBreakIntervals;
    }

    public void setDurationOfBreakIntervals(long durationOfBreakIntervals) {
        this.durationOfBreakIntervals = durationOfBreakIntervals;
    }
}
