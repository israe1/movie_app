package com.israel.movieapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.israel.movieapp.R;
import com.israel.movieapp.models.Movie;
import com.israel.movieapp.ui.movie_detail.MovieDetailActivity;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public static final String MOVIE = "movie";
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(ArrayList<Movie> movies, Context context) {
        mMovies = movies;
        mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        RatingBar mRatingBar;
        TextView mTextViewTitle;
        TextView mTextViewVoteCount;
        Movie mMovie;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ivMovie);
            mRatingBar = itemView.findViewById(R.id.rbMovie);
            mTextViewTitle = itemView.findViewById(R.id.tvMovieTitle);
            mTextViewVoteCount = itemView.findViewById(R.id.tvVoteCount);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie){
            mMovie = movie;
            mTextViewTitle.setText(mMovie.getTitle());
            mTextViewVoteCount.setText(String.valueOf(mMovie.getVote_count()));
            mRatingBar.setRating((float) (mMovie.getVote_average() / 2));

            Glide.with(itemView.getContext())
                    .load(BASE_IMAGE_URL.concat(mMovie.getPoster_path()))
                    .into(mImageView);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra(MOVIE, mMovie);
            mContext.startActivity(intent);
        }
    }
}
