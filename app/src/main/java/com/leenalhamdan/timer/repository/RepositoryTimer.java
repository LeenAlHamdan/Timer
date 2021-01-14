package com.leenalhamdan.timer.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leenalhamdan.timer.model.dao.TimerDao;
import com.leenalhamdan.timer.model.database.RoomDataBaseTimer;
import com.leenalhamdan.timer.model.entity.Timer;


public class RepositoryTimer
{
    private TimerDao timerDao;
    private LiveData<Timer> timer;

    private static MutableLiveData<Integer> result;

    public RepositoryTimer(Application application)
    {
        RoomDataBaseTimer db=RoomDataBaseTimer.getDataBase(application);

        timerDao =db.getTimerDao();

        result = new MutableLiveData<>();
    }

    public void insert(Timer... timers)
    {
        new TaskInsert(timerDao).execute(timers);
    }

    public void delete(Timer... timers)
    {
        new TaskDelete(timerDao).execute(timers);
    }

    public void update(Timer... timers)
    {
        new TaskUpdate(timerDao).execute(timers);
    }

    public LiveData<Timer> getTimer()
    {
        timer =timerDao.getTimer();
        return timer;
    }


    private static class TaskInsert extends AsyncTask<Timer,Void,Void>
    {
        private TimerDao timerDao;

        public TaskInsert(TimerDao timerDao)
        {
            this.timerDao = timerDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Timer... timers)
        {
            for (Timer timer:timers)
            {
                timerDao.insert(timer);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskDelete extends AsyncTask<Timer,Void,Void>
    {
        private TimerDao timerDao;

        public TaskDelete(TimerDao timerDao)
        {
            this.timerDao = timerDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Timer... timers)
        {
            for (Timer timer:timers)
            {
                timerDao.delete(timer);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskUpdate extends AsyncTask<Timer,Void,Void>
    {
        private TimerDao timerDao;

        public TaskUpdate(TimerDao timerDao)
        {
            this.timerDao = timerDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Timer... timers)
        {
            for (Timer timer:timers)
            {
                timerDao.update(timer);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
