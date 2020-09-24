package com.example.gogaga.retrofit;

import com.example.gogaga.apiinterface.PostApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRetrofit {
    PostApiInterface postApiInterface;

    public void getRetrofitInstance() {
        if (postApiInterface == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();

            postApiInterface = retrofit.create(PostApiInterface.class);
        }
    }

    public PostApiInterface getPostsInterface() {
        if (postApiInterface == null) {
            getRetrofitInstance();
        }
        return postApiInterface;
    }
}
