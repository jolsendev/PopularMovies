package com.example.jamie.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jamie.popularmovies.MainActivity;
import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.ReviewActivity;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;
import com.facebook.stetho.common.Util;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

/**
 * Created by a5w5nzz on 11/30/2016.
 */

public class DetailReviewAdapter extends CursorAdapter {

    public DetailReviewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final Context mContext = context;
        TextView reviewView = (TextView) view.findViewById(R.id.movie_review);
        TextView reviewAuthor= (TextView) view.findViewById(R.id.review_author);
        reviewAuthor.setText("By: "+cursor.getString(MovieDetailFragment.COL_REVIEW_AUTHOR));
        final String reviewContent = cursor.getString(MovieDetailFragment.COL_REVIEW_CONTENT);
        final long movieId = cursor.getLong(MovieDetailFragment.COL_MOVIE_ID);
        reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mContext, ReviewActivity.class);
                intent.setData(MovieContract.MovieEntry.buildMovieUri(movieId));
                Bundle mBundle = new Bundle();
                mBundle.putString(MovieContract.ReviewEntry.REVIEW_CONTENT, reviewContent);
                intent.putExtras(mBundle);
                mContext.startActivity(intent);
            }
        });
        reviewView.setText(reviewContent);
    }
}