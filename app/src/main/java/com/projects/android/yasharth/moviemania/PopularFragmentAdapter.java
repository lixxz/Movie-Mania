package com.projects.android.yasharth.moviemania;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PopularFragmentAdapter extends BaseAdapter {
    private Context context;

    public PopularFragmentAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        if (PopularFragment.movieDetails!=null) {
            return PopularFragment.movieDetails.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        return PopularFragment.movieDetails.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
        }
        else {
            imageView = (ImageView) convertView;
        }
        String url = "http://image.tmdb.org/t/p/w154/" +
                PopularFragment.movieDetails.get(position).getPoster_path();

        Picasso.with(context).
                load(url).
                placeholder(R.drawable.placeholder). //app doesn't work without it, why?
                fit().
                into(imageView);
        return imageView;
    }
}