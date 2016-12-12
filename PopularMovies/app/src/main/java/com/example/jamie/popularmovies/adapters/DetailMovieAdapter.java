package com.example.jamie.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jamie.popularmovies.MovieDetailView;
import com.example.jamie.popularmovies.R;

/**
 * Created by jamie on 12/8/16.
 */

public class DetailMovieAdapter extends CursorAdapter {

    public DetailMovieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_detail_list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView titleView = (TextView) view.findViewById(R.id.movie_title);
        titleView.setText(cursor.getString(MovieDetailView.COL_TITLE));


    }
}
