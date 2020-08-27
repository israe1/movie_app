package com.israel.movieapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.israel.movieapp.R;
import com.israel.movieapp.models.Movie;
import com.israel.movieapp.uiComponents.SpacingItemDecoration;
import com.israel.movieapp.utils.AppUtils;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Movie> mMovies;
    private int page = 1;
    private int total_pages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        mMovies = new ArrayList<>();
        final RecyclerView recyclerView = findViewById(R.id.rvMovies);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, 20, true));
        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        HomeViewModel viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        viewModel.getMovies(AppUtils.type.topRated, page);
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null)return;
                Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getLoad().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == null)return;
                recyclerView.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
                progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies == null)return;
                mMovies = movies;
                MovieAdapter adapter = new MovieAdapter(mMovies, HomeActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
        viewModel.getTotalPages().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null)return;
                total_pages = integer;
            }
        });
    }
}