package com.blueeagle.simple_retrofit_application.webserviceinterface;

import com.blueeagle.simple_retrofit_application.model.Feed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedServiceInterface {

    @GET("/posts")
    Call<List<Feed>> getAllPost();
}
