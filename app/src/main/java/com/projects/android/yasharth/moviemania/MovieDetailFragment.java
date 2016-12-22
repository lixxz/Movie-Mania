package com.projects.android.yasharth.moviemania;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projects.android.yasharth.moviemania.Adapters.MovieDetailFragmentAdapter;
import com.projects.android.yasharth.moviemania.Data.MovieContentDetails;
import com.projects.android.yasharth.moviemania.Network.ApiCall;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment implements ApiCall.Callback {
    private String movieId;
    private String backdrop_path;


    private RecyclerView mRecyclerView;
    private MovieDetailFragmentAdapter mAdapter;

    private ApiCall apiCall;

    public MovieDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        movieId = getArguments().getString("MovieId");
        backdrop_path = getArguments().getString("MovieBackdrop");

        ImageView imageView = (ImageView) rootView.findViewById(R.id.backdrop);
        imageView.setAdjustViewBounds(true);
        String url = "http://image.tmdb.org/t/p/w500/" + backdrop_path;

        Picasso.with(getContext()).
                load(url).
                fit().
                into(imageView);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_content);
        mAdapter = new MovieDetailFragmentAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        apiCall = new ApiCall(this, movieId);
    }

    public void onDataLoaded(MovieContentDetails movieDetails) {
        final MovieContentDetails movieContentDetails = movieDetails;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.RefreshData(movieContentDetails);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}
