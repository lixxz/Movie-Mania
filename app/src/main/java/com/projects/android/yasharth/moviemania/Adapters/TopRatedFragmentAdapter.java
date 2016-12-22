package com.projects.android.yasharth.moviemania.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.projects.android.yasharth.moviemania.R;
import com.projects.android.yasharth.moviemania.TopRatedFragment;
import com.squareup.picasso.Picasso;

public class TopRatedFragmentAdapter extends BaseAdapter {
    private Context context;

    public TopRatedFragmentAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        if (TopRatedFragment.sTopRatedInitialDetailses != null) {
            return TopRatedFragment.sTopRatedInitialDetailses.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        return TopRatedFragment.sTopRatedInitialDetailses.get(position);
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
                TopRatedFragment.sTopRatedInitialDetailses.get(position).getPoster_path();

        Picasso.with(context).
                load(url).
                placeholder(R.drawable.placeholder).
                fit().
                into(imageView);
        return imageView;
    }
}
