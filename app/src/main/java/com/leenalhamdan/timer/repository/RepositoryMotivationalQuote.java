package com.leenalhamdan.timer.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.leenalhamdan.timer.model.dao.JsonHolderApi;
import com.leenalhamdan.timer.model.dao.MotivationalQuoteDao;
import com.leenalhamdan.timer.model.database.RoomDataBaseTimer;
import com.leenalhamdan.timer.model.entity.MotivationalQuote;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RepositoryMotivationalQuote
{
    private MotivationalQuoteDao motivationalQuoteDao;
    private LiveData<String> motivationalQuoteContent;


    public RepositoryMotivationalQuote(Application application)
    {
        RoomDataBaseTimer db=RoomDataBaseTimer.getDataBase(application);

        motivationalQuoteDao =db.getMotivationalQuoteDao();

    }

    public void insert(MotivationalQuote... motivationalQuotes)
    {
        new TaskInsert(motivationalQuoteDao).execute(motivationalQuotes);
    }

    public void delete(MotivationalQuote... motivationalQuotes)
    {
        new TaskDelete(motivationalQuoteDao).execute(motivationalQuotes);
    }

    public void update(MotivationalQuote... motivationalQuotes)
    {
        new TaskUpdate(motivationalQuoteDao).execute(motivationalQuotes);
    }


    public LiveData<String> getMotivationalQuote()
    {
        motivationalQuoteContent =motivationalQuoteDao.getMotivationalQuote();
        return motivationalQuoteContent;
    }

    public void downloadMotivationalQuote()
    {
        new RepositoryMotivationalQuote.TaskDownload(motivationalQuoteDao).execute();
    }

    private static class TaskInsert extends AsyncTask<MotivationalQuote,Void,Void>
    {
        private MotivationalQuoteDao motivationalQuoteDao;

        public TaskInsert(MotivationalQuoteDao motivationalQuoteDao)
        {
            this.motivationalQuoteDao = motivationalQuoteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(MotivationalQuote... motivationalQuotes)
        {
            for (MotivationalQuote motivationalQuote:motivationalQuotes)
            {
                motivationalQuoteDao.insert(motivationalQuote);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskDownload extends AsyncTask<MotivationalQuote,Void,Void>
    {
        private MotivationalQuoteDao motivationalQuoteDao;

        public TaskDownload(MotivationalQuoteDao motivationalQuoteDao)
        {
            this.motivationalQuoteDao = motivationalQuoteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(MotivationalQuote... motivationalQuotes)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://pastebin.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            JsonHolderApi scalarService = retrofit.create(JsonHolderApi.class);

            Call<String> stringCall = scalarService.getMotivationalQuote("raw/jmhKjPLD");

            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String responseString = response.body();

                        MotivationalQuote motivationalQuote =new MotivationalQuote(responseString);

                        new RepositoryMotivationalQuote.TaskInsert(motivationalQuoteDao).doInBackground(motivationalQuote);

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Paper.book().write("Throwable", t.getMessage());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskDelete extends AsyncTask<MotivationalQuote,Void,Void>
    {
        private MotivationalQuoteDao motivationalQuoteDao;

        public TaskDelete(MotivationalQuoteDao motivationalQuoteDao)
        {
            this.motivationalQuoteDao = motivationalQuoteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(MotivationalQuote... motivationalQuotes)
        {
            for (MotivationalQuote motivationalQuote:motivationalQuotes)
            {
                motivationalQuoteDao.delete(motivationalQuote);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class TaskUpdate extends AsyncTask<MotivationalQuote,Void,Void>
    {
        private MotivationalQuoteDao motivationalQuoteDao;

        public TaskUpdate(MotivationalQuoteDao motivationalQuoteDao)
        {
            this.motivationalQuoteDao = motivationalQuoteDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(MotivationalQuote... motivationalQuotes)
        {
            for (MotivationalQuote motivationalQuote:motivationalQuotes)
            {
                motivationalQuoteDao.update(motivationalQuote);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
