package com.projects.android.yasharth.moviemania;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.android.yasharth.moviemania.Data.MovieContentDetails;

public class MovieDetailFragmentAdapter extends
        RecyclerView.Adapter<MovieDetailFragmentAdapter.ViewHolder> {
    private Context mContext;
    private MovieContentDetails mMovieContentDetails;

    public MovieDetailFragmentAdapter(Context context) {
        mContext = context;
    }

    public void RefreshData(MovieContentDetails movieContentDetails) {
        mMovieContentDetails = movieContentDetails;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView release_date;
        public final TextView duration;
        public final TextView genre;
        public final TextView rating;
        public final TextView description;


        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.movie_title);
            release_date = (TextView) v.findViewById(R.id.release_date);
            duration = (TextView) v.findViewById(R.id.duration);
            genre = (TextView) v.findViewById(R.id.genre);
            rating = (TextView) v.findViewById(R.id.rating);
            description = (TextView) v.findViewById(R.id.description);
        }
    }

    @Override
    public MovieDetailFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View movieContent = inflater.inflate(R.layout.content_movie_detail_fragment, parent, false);

        return new ViewHolder(movieContent);
    }

    @Override
    public void onBindViewHolder(MovieDetailFragmentAdapter.ViewHolder holder, int position) {
        if (mMovieContentDetails != null) {
            holder.duration.setText(mMovieContentDetails.getRuntime());
            holder.release_date.setText(mMovieContentDetails.getRelease_date());
            holder.description.setText(mMovieContentDetails.getDescription());
            holder.genre.setText(mMovieContentDetails.getGenres());
            holder.rating.setText(mMovieContentDetails.getRating());
            holder.title.setText(mMovieContentDetails.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}