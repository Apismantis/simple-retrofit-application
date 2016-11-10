package com.blueeagle.simple_retrofit_application.webserviceinterface;

import com.blueeagle.simple_retrofit_application.model.Comment;
import com.blueeagle.simple_retrofit_application.model.Feed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WebServiceInterface {

    @GET("/posts")
    Call<List<Feed>> getAllPost();

    @GET("/posts/{postId}/comments")
    Call<List<Comment>> getComments(@Path("postId") int postId);
}
