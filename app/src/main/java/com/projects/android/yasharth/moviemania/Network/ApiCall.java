package com.projects.android.yasharth.moviemania.Network;

import android.util.Log;

import com.projects.android.yasharth.moviemania.BuildConfig;
import com.projects.android.yasharth.moviemania.Data.MovieContentDetails;
import com.projects.android.yasharth.moviemania.Data.MovieReviews;
import com.projects.android.yasharth.moviemania.Data.TrailerDetails;
import com.projects.android.yasharth.moviemania.MovieDetailFragment;
import com.projects.android.yasharth.moviemania.Utility.Formatting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
    All the network logic will be here
    TODO: shift the JSON loading functions to Utility
 */
public class ApiCall {
    private Formatting formatting = new Formatting();

    private MovieDetailFragment movieDetailFragment;

    private MovieContentDetails movieContentDetails;
    private ArrayList<TrailerDetails> trailerDetails = new ArrayList<>();
    private ArrayList<MovieReviews> movieReviews = new ArrayList<>();

    private String baseUrl;
    private String id;
    private String apiKey;

    public ApiCall(MovieDetailFragment movieDetailFragment, String movieId) {
        this.movieDetailFragment = movieDetailFragment;
        baseUrl = "http://api.themoviedb.org/3/movie";
        id = movieId;
        apiKey = "api_key";
        loadDetails();
        loadTrailers();
        loadReviews();
    }

    public interface Callback {
        public void onDataLoaded(MovieContentDetails movieContentDetails);
    }

    public void loadDetails() {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addPathSegment(id);
        urlBuilder.addQueryParameter(apiKey, BuildConfig.tmdbApiKey);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new okhttp3.Callback() {
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

                movieDetailFragment.onDataLoaded(movieContentDetails);
            }
        });
    }

    public void loadTrailers() {
        final String trailerPath = "videos";

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addPathSegment(id);
        urlBuilder.addPathSegment(trailerPath);
        urlBuilder.addQueryParameter(apiKey, BuildConfig.tmdbApiKey);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
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
                    getMovieTrailersFromJson(responseData);
                } catch (JSONException e) {
                    Log.e("JSON", "trailer respone problem");
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadReviews() {
        final String trailerPath = "reviews";

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addPathSegment(id);
        urlBuilder.addPathSegment(trailerPath);
        urlBuilder.addQueryParameter(apiKey, BuildConfig.tmdbApiKey);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
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
                    getUserReviewsFromJson(responseData);
                } catch (JSONException e) {
                    Log.e("JSON", "review respone problem");
                    e.printStackTrace();
                }
            }
        });
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
            if (i < movieGenres.length() - 1)
                genres += ", ";
        }

        movieContentDetails = new MovieContentDetails(movieJson.getString(TITLE),
                movieJson.getString(RELEASE_DATE),
                formatting.formatRuntime(movieJson.getInt(RUNTIME)),
                genres,
                formatting.formatRating(movieJson.getDouble(RATING)),
                movieJson.getString(DESCRIPTION));
    }

    private void getMovieTrailersFromJson(String trailerJsonStr)
            throws JSONException {

        final String KEY = "key";
        final String NAME = "name";
        final String RESULT = "results";

        JSONObject trailerJSON = new JSONObject(trailerJsonStr);
        JSONArray trailerArray = trailerJSON.getJSONArray(RESULT);

        for(int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailerList = trailerArray.getJSONObject(i);

            trailerDetails.add(i, new TrailerDetails(trailerList.getString(KEY),
                    trailerList.getString(NAME)
            ));
        }
    }

    private void getUserReviewsFromJson(String reviewJsonStr)
            throws JSONException {

        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String RESULT = "results";

        JSONObject reviewJSON = new JSONObject(reviewJsonStr);
        JSONArray reviewArray = reviewJSON.getJSONArray(RESULT);

        for(int i = 0; i < reviewArray.length(); i++) {
            JSONObject reviewList = reviewArray.getJSONObject(i);

            movieReviews.add(i, new MovieReviews(reviewList.getString(AUTHOR),
                    reviewList.getString(CONTENT)
            ));
        }
    }
}
