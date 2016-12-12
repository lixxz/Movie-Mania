package com.projects.android.yasharth.moviemania;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MovieDetailFragmentAdapter extends BaseAdapter {
    private Context context;

    public MovieDetailFragmentAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return 1;
    }

    public Object getItem(int position) {
        return MovieDetailFragment.movieContentDetails;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView title = (TextView) convertView.findViewById(R.id.movie_title);
        title.setText(MovieDetailFragment.movieContentDetails.getTitle());

        final TextView release_date = (TextView) convertView.findViewById(R.id.release_date);
        release_date.setText(MovieDetailFragment.movieContentDetails.getRelease_date());

        final TextView duration = (TextView) convertView.findViewById(R.id.duration);
        duration.setText(MovieDetailFragment.movieContentDetails.getRuntime());

        final TextView genre = (TextView) convertView.findViewById(R.id.genre);
        genre.setText(MovieDetailFragment.movieContentDetails.getGenres());

        final TextView rating = (TextView) convertView.findViewById(R.id.rating);
        rating.setText(MovieDetailFragment.movieContentDetails.getRating());

        final TextView description = (TextView) convertView.findViewById(R.id.description);
        description.setText(MovieDetailFragment.movieContentDetails.getDescription());

        return convertView;
    }
}

