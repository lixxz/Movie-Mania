package com.projects.android.yasharth.moviemania.Data;

import android.os.Parcel;
import android.os.Parcelable;



public class MovieContentDetails implements Parcelable {

    private String title;
    private String release_date;
    private int runtime;
    private String genres;
    private Double rating;
    private String description;


    public MovieContentDetails(String title, String release_date, int runtime, String genres,
                               Double rating, String description) {
        this.title = title;
        this.release_date = release_date;
        this.runtime = runtime;
        this.genres = genres;
        this.rating = rating;
        this.description = description;
    }

    private MovieContentDetails(Parcel in) {
        title = in.readString();
        release_date = in.readString();
        runtime = in.readInt();
        genres = in.readString();
        rating = in.readDouble();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeInt(runtime);
        parcel.writeString(genres);
        parcel.writeDouble(rating);
        parcel.writeString(description);
    }

    public static final Parcelable.Creator<MovieContentDetails> CREATOR
            = new Parcelable.Creator<MovieContentDetails>() {
        public MovieContentDetails createFromParcel(Parcel in) {
            return new MovieContentDetails(in);
        }

        public MovieContentDetails[] newArray(int size) {
            return new MovieContentDetails[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getRuntime() {
        return Integer.toString(runtime);
    }

    public String getGenres() {
        return genres;
    }

    public String getRating() {
        return Double.toString(rating);
    }

    public String getDescription() {
        return description;
    }
}
