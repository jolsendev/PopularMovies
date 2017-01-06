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

import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.ReviewActivity;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;

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
        TextView trailerView = (TextView) view.findViewById(R.id.movie_review);
        final String reviewContent = cursor.getString(MovieDetailFragment.COL_REVIEW_CONTENT);

        trailerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mContext, ReviewActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString(MovieContract.ReviewEntry.REVIEW_CONTENT, reviewContent);
                intent.putExtras(mBundle);
                mContext.startActivity(intent);
            }
        });
        trailerView.setText(reviewContent);
    }
}