package com.leenalhamdan.timer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leenalhamdan.timer.model.entity.Timer;
import com.leenalhamdan.timer.repository.RepositoryTimer;

import java.util.List;

public class ViewModelTimer extends AndroidViewModel
{
    private RepositoryTimer repositoryTimer;
    private LiveData<Timer> timer;

    public ViewModelTimer(@NonNull Application application)
    {
        super(application);

        repositoryTimer= new RepositoryTimer(application);
    }

    public void insert(Timer... timers)
    {
        repositoryTimer.insert(timers);
    }

    public void delete(Timer... timers)
    {
        repositoryTimer.delete(timers);
    }

    public void update(Timer... timers)
    {
        repositoryTimer.update(timers);
    }

    public LiveData<Timer> getTimer()
    {
        timer =repositoryTimer.getTimer();
        return timer;
    }

}
