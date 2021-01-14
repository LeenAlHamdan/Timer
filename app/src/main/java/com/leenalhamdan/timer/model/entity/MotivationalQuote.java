package com.leenalhamdan.timer.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "MotivationalQuote")
public class MotivationalQuote
{
    //i put it in separated class so that every user could hava his own motivational quote
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;

    @SerializedName("motivationalQuoteContent")
    @ColumnInfo(name = "motivationalQuoteContent")
    private String motivationalQuoteContent;

    public MotivationalQuote()
    {
    }

    public MotivationalQuote(String motivationalQuoteContent)
    {
        this.motivationalQuoteContent = motivationalQuoteContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMotivationalQuoteContent() {
        return motivationalQuoteContent;
    }

    public void setMotivationalQuoteContent(String motivationalQuoteContent) {
        this.motivationalQuoteContent = motivationalQuoteContent;
    }
}
