package com.example.imgur.NetworkUtils;

import android.content.Context;
import android.util.Log;

import com.example.imgur.Models.Comment;
import com.example.imgur.Models.CommentResponse;
import com.example.imgur.Models.Image;
import com.example.imgur.Models.ImageResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private Retrofit retrofit;
    ApiCalls apiCalls;
    Context context;

    public ApiManager(Context context) {

        this.context = context;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .addHeader("Client-ID", "ef25cd11b18d3f4")
                                .addHeader("Authorization", "Bearer 7d8c6a44a6d5b733405de7675d4792ea373c7e36")
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://api.imgur.com/3/")
                .build();

        apiCalls = retrofit.create(ApiCalls.class);
    }

    public void getImage(String id, final ImageListener listener) {
        Call<ImageResponse> call = apiCalls.getImage(id);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, retrofit2.Response<ImageResponse> response) {
                Log.d("Retro response", response.body().getStatus());
                Image image = response.body().getData();
                listener.imageRecieved(image);
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d("Retro Response", t.getMessage());
            }
        });
    }

    public void getComments(String id, final CommentListener commentListener) {
        Call<CommentResponse> call = apiCalls.getComments(id);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, retrofit2.Response<CommentResponse> response) {
                ArrayList<Comment> comments = response.body().getData();
                commentListener.commentReceived(comments);
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Log.d("Retro Response", t.getMessage());
            }
        });
    }

    public void postComment(String id, String comment, final CommentSuccessListener successListener) {
        Call<JsonElement> call = apiCalls.postComment(id, comment);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                JsonElement element = response.body().getAsJsonObject();
                JsonElement dataElement = ((JsonObject) element).getAsJsonObject("data");
                String id = ((JsonObject) dataElement).get("id").getAsString();
                successListener.onSucces(id);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Retro Response", t.getMessage());
            }
        });
    }

    public void getMp4(final Image image, final File file, final VideoListener videoListener) {

        Call<ResponseBody> call = apiCalls.getMp4(image.getMp4());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(response.body().bytes());
                    videoListener.getVideo(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Retro Response", t.getMessage());
            }
        });
    }
}
