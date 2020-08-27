package com.israel.movieapp.ui.movie_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.israel.movieapp.R;
import com.israel.movieapp.models.Movie;
import com.israel.movieapp.ui.home.MovieAdapter;

public class MovieDetailActivity extends AppCompatActivity {

    private boolean mFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initToolbar();
        initUi();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Movie Detail");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }

    @SuppressLint("SetTextI18n")
    private void initUi() {
        mFav = false;
        if (!getIntent().hasExtra(MovieAdapter.MOVIE))return;
        Movie movie =  getIntent().getParcelableExtra(MovieAdapter.MOVIE);
        if (movie == null)return;
        Glide.with(this)
                .load(MovieAdapter.BASE_IMAGE_URL.concat(movie.getPoster_path()))
                .into((ImageView) findViewById(R.id.ivMovie));
        TextView textViewTitle = findViewById(R.id.tvMovieTitle);
        TextView textViewReleaseDate = findViewById(R.id.tvReleaseDate);
        TextView textViewNote = findViewById(R.id.tvNote);
        TextView textViewOverview = findViewById(R.id.tvOverView);
        final ImageView imageViewFav = findViewById(R.id.ivFavorite);

        textViewTitle.setText(movie.getTitle());
        textViewReleaseDate.setText("Release date: ".concat(movie.getRelease_date()));
        textViewNote.setText(String.valueOf(movie.getVote_average()).concat(" / 10"));
        textViewOverview.setText(movie.getOverview());

        imageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFav = !mFav;
                imageViewFav.setImageDrawable(ContextCompat.getDrawable(MovieDetailActivity.this,
                        mFav ? R.drawable.ic_favorite_24 : R.drawable.ic_favorite_border_24));
            }
        });
    }
}