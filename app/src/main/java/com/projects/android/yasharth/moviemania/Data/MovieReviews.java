package com.projects.android.yasharth.moviemania.Data;

import android.os.Parcel;
import android.os.Parcelable;


public class MovieReviews implements Parcelable {

    private String author;
    private String content;


    public MovieReviews(String author, String content) {
        this.author = author;
        this.content = content;
    }

    private MovieReviews(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
    }

    public static final Parcelable.Creator<MovieReviews> CREATOR
            = new Parcelable.Creator<MovieReviews>() {
        public MovieReviews createFromParcel(Parcel in) {
            return new MovieReviews(in);
        }

        public MovieReviews[] newArray(int size) {
            return new MovieReviews[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

}