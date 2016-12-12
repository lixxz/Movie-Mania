package com.projects.android.yasharth.moviemania;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_activity);

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString("MovieId", getIntent().getExtras().get("MovieId").toString());
        args.putString("MovieBackdrop", getIntent().getExtras().getString("MovieBackdrop"));
        movieDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().
                add(R.id.detail_fragment_container, movieDetailFragment).
                commit();

    }
}
