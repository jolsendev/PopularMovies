package com.example.jamie.popularmovies.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;

import static android.R.attr.id;

/**
 * Created by a5w5nzz on 11/30/2016.
 */

public class DetailTrailerAdapter extends CursorAdapter {

    public DetailTrailerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private final String BASE_URL= "http://www.youtube.com/watch?v=" ;
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final Context mContext = context;
        TextView trailerView = (TextView) view.findViewById(R.id.movie_trailer);
        String detail = cursor.getString(MovieDetailFragment.COL_TRAILER_NAME);
        final String video_source = cursor.getString(MovieDetailFragment.COL_TRAILER_SOURCE);
        trailerView.setText(detail);

        trailerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_source));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(BASE_URL+ video_source));
                        try {
                            mContext.startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            mContext.startActivity(webIntent);
                        }

            }
        });
    }
}
