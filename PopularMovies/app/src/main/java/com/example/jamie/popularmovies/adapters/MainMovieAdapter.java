package com.example.jamie.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jamie.popularmovies.fragments.MainMovieFragment;
import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.Utility;
import com.squareup.picasso.Picasso;

/**
 * Created by a5w5nzz on 11/30/2016.
 */

public class MainMovieAdapter extends CursorAdapter {


    public MainMovieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.gridview_image_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String path = cursor.getString(MainMovieFragment.COL_POSTER_PATH);
        String title = cursor.getString(MainMovieFragment.COL_TITLE);
        String date = cursor.getString(MainMovieFragment.COL_RELEASE_DATE);

        TextView dateView = (TextView) view.findViewById(R.id.gridview_date);
        String [] year = date.split("-");
        dateView.setText(year[0]);
        TextView titleView = (TextView) view.findViewById(R.id.gridview_title);
       titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setText(title);
//        titleView.setSelected(true);
       titleView.setSingleLine(true);
        ImageView imageItem = (ImageView) view.findViewById(R.id.thumbnail);
        Picasso.with(context)
                .load(Utility.getImagePath(path)).placeholder(R.drawable.popcorntime)
                .into(imageItem);
    }
}
