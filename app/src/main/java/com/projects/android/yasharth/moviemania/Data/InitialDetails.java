package com.projects.android.yasharth.moviemania.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class InitialDetails implements Parcelable {
    private String poster_path;
    private String backdrop_path;
    private Integer id;


    public InitialDetails(String poster_path, String backdrop_path, Integer id) {
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.id = id;
    }

    private InitialDetails(Parcel in) {
        poster_path = in.readString();
        backdrop_path = in.readString();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeInt(id);
    }

    public static final Parcelable.Creator<InitialDetails> CREATOR
            = new Parcelable.Creator<InitialDetails>() {
        public InitialDetails createFromParcel(Parcel in) {
            return new InitialDetails(in);
        }

        public InitialDetails[] newArray(int size) {
            return new InitialDetails[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public Integer getId() {
        return id;
    }
}
