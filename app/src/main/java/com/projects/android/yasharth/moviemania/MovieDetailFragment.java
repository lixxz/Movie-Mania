package com.projects.android.yasharth.moviemania;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projects.android.yasharth.moviemania.Data.MovieContentDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailFragment extends Fragment {
    public static String movieId;
    public static String backdrop_path;

    public static MovieDetailFragmentAdapter detailFragmentAdapter;

    public static MovieContentDetails movieContentDetails;

    public MovieDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (getArguments() != null) {
            movieId = getArguments().getString("MovieId");
            backdrop_path = getArguments().getString("MovieBackdrop");
        }

        ImageView imageView = (ImageView) rootView.findViewById(R.id.backdrop);
        imageView.setAdjustViewBounds(true);
        String url = "http://image.tmdb.org/t/p/w500/" + backdrop_path;

        Picasso.with(getContext()).
                load(url).
                placeholder(R.drawable.ic_video_library_black_24dp).
                fit().
                into(imageView);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDetails();
        loadTrailers();
        loadReviews();
    }

    public void loadDetails() {
        final String baseUrl = "http://api.themoviedb.org/3/movie";
        final String id = movieId;
        final String apiKey = "api_key";

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addPathSegment(id);
        urlBuilder.addQueryParameter(apiKey, BuildConfig.tmdbApiKey);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String responseData = response.body().string();
                try {
                    getMovieDetailsFromJson(responseData);
                } catch (JSONException e) {
                    Log.e("JSON", "respone problem");
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public void loadTrailers() {

    }

    public void loadReviews() {

    }

    private void getMovieDetailsFromJson(String movieJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String TITLE = "title";
        final String RELEASE_DATE = "release_date";
        final String RUNTIME = "runtime";
        final String GENRES = "genres";
        final String GENRE_NAME = "name";
        final String RATING = "vote_average";
        final String DESCRIPTION = "overview";

        JSONObject movieJson = new JSONObject(movieJsonStr);

        // Extract different genres
        JSONArray movieGenres = movieJson.getJSONArray(GENRES);
        JSONObject genre;
        String genres = "";
        for (int i = 0; i < movieGenres.length(); i++) {
            genre = movieGenres.getJSONObject(i);
            genres += genre.getString(GENRE_NAME);
            if (i < movieGenres.length())
                genres += ", ";
        }

        movieContentDetails = new MovieContentDetails(movieJson.getString(TITLE),
                movieJson.getString(RELEASE_DATE),
                movieJson.getInt(RUNTIME),
                genres,
                movieJson.getDouble(RATING),
                movieJson.getString(DESCRIPTION));

        Log.v("LOOK HERE DATA", movieContentDetails.toString());
    }
}
