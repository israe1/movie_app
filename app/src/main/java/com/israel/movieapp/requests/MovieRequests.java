package com.israel.movieapp.requests;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MovieRequests {
    @GET("movie/top_rated")
    @Headers({"Content-Type: Application/json", "Accept: application/json"})
    Call<ResponseBody> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/popular")
    @Headers({"Content-Type: Application/json", "Accept: application/json"})
    Call<ResponseBody> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );
}
