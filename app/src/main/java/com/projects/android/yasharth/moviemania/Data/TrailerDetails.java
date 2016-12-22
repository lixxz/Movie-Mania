package com.projects.android.yasharth.moviemania.Data;

import android.os.Parcel;
import android.os.Parcelable;


public class TrailerDetails implements Parcelable {

    private String name;
    private String key;


    public TrailerDetails(String key, String name) {
        this.name = name;
        this.key = key;
    }

    private TrailerDetails(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
    }

    public static final Parcelable.Creator<TrailerDetails> CREATOR
            = new Parcelable.Creator<TrailerDetails>() {
        public TrailerDetails createFromParcel(Parcel in) {
            return new TrailerDetails(in);
        }

        public TrailerDetails[] newArray(int size) {
            return new TrailerDetails[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

}
