package com.projects.android.yasharth.moviemania.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetail implements Parcelable {
    String poster_path;
    String title;
    String release_date;
    String synopsis;
    Double vote_average;
    Integer id;


    public MovieDetail(String poster_path, String title, String release_date,
                                String synopsis, Double vote_average, Integer id) {
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.synopsis = synopsis;
        this.vote_average = vote_average;
        this.id = id;
    }

    private MovieDetail(Parcel in) {
        poster_path = in.readString();
        title = in.readString();
        release_date = in.readString();
        synopsis = in.readString();
        vote_average = in.readDouble();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeString(synopsis);
        parcel.writeDouble(vote_average);
        parcel.writeInt(id);
    }

    public static final Parcelable.Creator<MovieDetail> CREATOR
            = new Parcelable.Creator<MovieDetail>() {
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public Integer getId() {
        return id;
    }
}
