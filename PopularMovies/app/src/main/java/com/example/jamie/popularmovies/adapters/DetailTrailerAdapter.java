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
 * Created by a5w5nzz on 11/30/2016.
 */

public class DetailTrailerAdapter extends CursorAdapter {

    public DetailTrailerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView trailerView = (TextView) view.findViewById(R.id.movie_trailer);
        trailerView.setText(cursor.getString(MovieDetailView.COL_TRAILER_NAME));
    }
}
