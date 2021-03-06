package com.projects.android.yasharth.moviemania;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.projects.android.yasharth.moviemania.Adapters.PopularFragmentAdapter;
import com.projects.android.yasharth.moviemania.Data.InitialDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PopularFragment extends Fragment {

    public final String SORT_ORDER = "popular";
    public static final String SORT_ORDER_POPULAR = "popular";
    public static final String SORT_ORDER_TOP_RATED = "top_Rated";
    public static final String SORT_ORDER_FAVORITES = "Favorites";

    public PopularFragmentAdapter popularFragmentAdapter;

    public static ArrayList<InitialDetails> sPopularInitialDetailses = new ArrayList<>();

    private static int sItem = 0;

    public PopularFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_screen, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        popularFragmentAdapter = new PopularFragmentAdapter(getContext());
        gridView.setAdapter(popularFragmentAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("MovieId", sPopularInitialDetailses.get(position).getId());
                intent.putExtra("MovieBackdrop",
                        sPopularInitialDetailses.get(position).getBackdrop_path());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (sPopularInitialDetailses.size() == 0) {
            loadPosters(SORT_ORDER);
        }
    }

    public void loadPosters(String sort) {
        final String baseUrl = "http://api.themoviedb.org/3/movie";
        final String apiKey = "api_key";
        final String page = "page";
        final String sortType = sort;

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addPathSegment(sortType);
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
                    getMovieDataFromJson(responseData);
                } catch (JSONException e) {
                    Log.e("JSON", "respone problem");
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popularFragmentAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    private void getMovieDataFromJson(String movieJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String RESULT = "results";
        final String POSTER_PATH = "poster_path";
        final String BACKDROP_PATH = "backdrop_path";
        final String MOVIE_ID = "id";

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(RESULT);

        for(int i = 0; i < movieArray.length(); i++) {

            // Get the JSON object representing the movies
            JSONObject movieList = movieArray.getJSONObject(i);

            sPopularInitialDetailses.add(sItem, new InitialDetails(movieList.getString(POSTER_PATH),
                    movieList.getString(BACKDROP_PATH),
                    movieList.getInt(MOVIE_ID)));
            sItem++;
        }
    }
}