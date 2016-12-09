package com.example.jamie.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jamie.popularmovies.R;

/**
 * Created by a5w5nzz on 11/30/2016.
 */

public class DetailReviewAdapter extends CursorAdapter {
    private ImageView imageItem;

    public DetailReviewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_movie_detail_view, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



    }
}
