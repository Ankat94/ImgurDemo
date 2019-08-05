package com.example.imgur.NetworkUtils;

import com.example.imgur.Models.CommentResponse;
import com.example.imgur.Models.ImageResponse;
import com.google.gson.JsonElement;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiCalls {

    @GET("image/{imageId}")
    Call<ImageResponse> getImage(@Path("imageId") String id);

    @GET("gallery/image/{imageId}/comments")
    Call<CommentResponse> getComments(@Path("imageId") String id);

    @FormUrlEncoded
    @POST("comment")
    Call<JsonElement> postComment(@Field("image_id") String id, @Field("comment") String comment);

    @GET
    Call<ResponseBody> getMp4(@Url String url);

}
