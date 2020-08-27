package com.israel.movieapp.ui.home;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.israel.movieapp.models.Movie;
import com.israel.movieapp.utils.APIUtils;
import com.israel.movieapp.utils.AppUtils;
import com.israel.movieapp.utils.AppUtils.type;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    public static final String TAG = "HomeViewModel";
    private MutableLiveData<String> mError;
    private MutableLiveData<Boolean> mLoad;
    private MutableLiveData<Integer> mTotalPages;
    private MutableLiveData<ArrayList<Movie>> mMovies;


    public HomeViewModel(){
        mError = new MutableLiveData<>();
        mLoad = new MutableLiveData<>();
        mTotalPages = new MutableLiveData<>();
        mMovies = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getTotalPages() {
        return mTotalPages;
    }

    public MutableLiveData<Boolean> getLoad() {
        return mLoad;
    }

    public MutableLiveData<String> getError() {
        return mError;
    }

    public MutableLiveData<ArrayList<Movie>> getMovies() {
        return mMovies;
    }

    public void getMovies(type type, int page){
        mLoad.setValue(true);
        Call<ResponseBody> call;
        switch (type){
            case topRated:
            default:
                call = APIUtils.getMoviesRequests().getTopRatedMovies(AppUtils.API_KEY, page);
                break;
            case popular:
                call = APIUtils.getMoviesRequests().getPopularMovies(AppUtils.API_KEY, page);
                break;
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mLoad.setValue(false);
                if (response.isSuccessful() && response.body() != null){
                    try{
                        String output = response.body().string();
                        JSONObject object = new JSONObject(output);
                        mTotalPages.setValue(object.getInt("total_pages"));
                        ArrayList<Movie> movies = new Gson().fromJson(object.getJSONArray("results").toString(), new TypeToken<ArrayList<Movie>>(){}.getType());
                        mMovies.setValue(movies);
                    }catch (Exception e){
                        mError.setValue("An error occurred");
                    }
                }else {
                    mError.setValue("Not found");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mLoad.setValue(false);
                mError.setValue("Please check your Internet connection");
            }
        });
    }
}
