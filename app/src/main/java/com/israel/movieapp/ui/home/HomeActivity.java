package com.israel.movieapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.israel.movieapp.R;
import com.israel.movieapp.models.Movie;
import com.israel.movieapp.uiComponents.SpacingItemDecoration;
import com.israel.movieapp.utils.AppUtils;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Movie> mMovies;
    private int page = 1;
    private int totalPages = 1;
    private HomeViewModel mViewModel;
    private AppUtils.type mType;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        mMovies = new ArrayList<>();
        final View view = findViewById(R.id.lytPages);
        final TextView textViewPage = findViewById(R.id.tvPage);
        final TextView textViewTotalPages = findViewById(R.id.tvTotalPages);
        final TextView textViewTitle = findViewById(R.id.tvType);
        final RecyclerView recyclerView = findViewById(R.id.rvMovies);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, 20, true));
        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        mViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        mType = AppUtils.type.topRated;
        mTitle = "Top Rated";
        mViewModel.getMovies(mType, page);
        mViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null)return;
                Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        mViewModel.getLoad().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == null)return;
                recyclerView.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
                progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                view.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
            }
        });
        mViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies == null)return;
                mMovies = movies;
                MovieAdapter adapter = new MovieAdapter(mMovies, HomeActivity.this);
                recyclerView.setAdapter(adapter);
                textViewPage.setText(String.valueOf(page));
                textViewTitle.setText(mTitle);
            }
        });
        mViewModel.getTotalPages().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null)return;
                totalPages = integer;
                textViewTotalPages.setText(String.valueOf(totalPages));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_previous:
                if (page == 1)return false;
                page--;
                mViewModel.getMovies(mType, page);
                break;
            case R.id.option_next:
                if (page == totalPages)return false;
                page ++;
                mViewModel.getMovies(mType, page);
                break;
            case R.id.option_top_rated:
                page = 1;
                mTitle = "Top Rated";
                mType = AppUtils.type.topRated;
                mViewModel.getMovies(mType, page);
                break;
            case R.id.option_popular:
                page = 1;
                mTitle = "Popular";
                mType = AppUtils.type.popular;
                mViewModel.getMovies(mType, page);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}