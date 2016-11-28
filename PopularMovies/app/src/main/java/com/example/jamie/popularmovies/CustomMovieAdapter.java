package com.example.jamie.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.jamie.popularmovies.movie_objects.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 * Created by A5W5NZZ on 8/31/2016.
 */
public class CustomMovieAdapter extends ArrayAdapter<ContentValues> {
    Context mContext;
    List<Movie> mMovieList;
    LayoutInflater mInflater;

    public CustomMovieAdapter(Context mContext, List<ContentValues> mMovieList) {
        super(mContext, 0, mMovieList);
        this.mContext = mContext;
        this.mMovieList = mMovieList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
             Movie mMovie = getItem(position);
             view = mInflater.inflate(R.layout.gridview_image_item, null);
             ImageView imageItem = (ImageView) view.findViewById(R.id.thumbnail);
             Picasso.with(mContext)
                     .load(mMovie.getPosterPath())
                     .placeholder(R.drawable.popcorntime)
                     .into(imageItem);
        return view;
    }

    public void add(Movie movie){
        mMovieList.add(movie);

    }

}
