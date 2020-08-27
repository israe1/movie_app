package com.israel.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private long id;
    private long vote_count;
    private double popularity;
    private double vote_average;
    private String poster_path;
    private String backdrop_path;
    private String original_title;
    private String title;
    private String overview;
    private String release_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    protected Movie(Parcel in) {
        id = in.readLong();
        vote_count = in.readLong();
        popularity = in.readDouble();
        vote_average = in.readDouble();
        poster_path = in.readString();
        backdrop_path = in.readString();
        original_title = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(vote_count);
        parcel.writeDouble(popularity);
        parcel.writeDouble(vote_average);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(original_title);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", vote_count=" + vote_count +
                ", popularity=" + popularity +
                ", vote_average=" + vote_average +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", original_title='" + original_title + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
