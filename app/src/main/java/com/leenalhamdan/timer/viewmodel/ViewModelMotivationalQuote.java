package com.leenalhamdan.timer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leenalhamdan.timer.model.entity.MotivationalQuote;
import com.leenalhamdan.timer.repository.RepositoryMotivationalQuote;

public class ViewModelMotivationalQuote extends AndroidViewModel
{
    private RepositoryMotivationalQuote repositoryMotivationalQuote;
    private LiveData<String> motivationalQuote;

    public ViewModelMotivationalQuote(@NonNull Application application)
    {
        super(application);

        repositoryMotivationalQuote= new RepositoryMotivationalQuote(application);
    }

    public void insert(MotivationalQuote... motivationalQuotes)
    {
        repositoryMotivationalQuote.insert(motivationalQuotes);
    }

    public void delete(MotivationalQuote... motivationalQuotes)
    {
        repositoryMotivationalQuote.delete(motivationalQuotes);
    }

    public void update(MotivationalQuote... motivationalQuotes)
    {
        repositoryMotivationalQuote.update(motivationalQuotes);
    }

    public LiveData<String> getMotivationalQuote()
    {
        motivationalQuote =repositoryMotivationalQuote.getMotivationalQuote();
        return motivationalQuote;
    }

    public void downloadMotivationalQuote()
    {
         repositoryMotivationalQuote.downloadMotivationalQuote();
    }
}
