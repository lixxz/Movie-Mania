package com.projects.android.yasharth.moviemania.Utility;


public class Formatting {

    public String formatDate(String date) {
        //TODO
        return null;

    }

    public String formatRuntime(Integer runtime) {
        return runtime + " min";
    }

    public String formatRating(Double rating) {
        return (int)(rating*10) + "%";
    }
}
