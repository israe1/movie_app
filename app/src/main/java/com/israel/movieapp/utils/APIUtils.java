package com.israel.movieapp.utils;

import com.israel.movieapp.requests.MovieRequests;

public class APIUtils {
    public static MovieRequests getMoviesRequests(){
        return RetrofitUtils.getRetrofitClient().create(MovieRequests.class);
    }
}
