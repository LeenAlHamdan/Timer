package com.leenalhamdan.timer.model.dao;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface JsonHolderApi
{
    @GET
    Call<String> getMotivationalQuote(@Url String url);
}
