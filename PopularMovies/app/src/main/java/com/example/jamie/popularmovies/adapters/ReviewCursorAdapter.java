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

public class ReviewCursorAdapter extends CursorAdapter {
    private ImageView imageItem;

    public ReviewCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //activity_movie_detail_view
        View view = LayoutInflater.from(context).inflate(R.layout.activity_movie_detail_view, parent, false);
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        imageItem = (ImageView) view.findViewById(R.id.detail_movie_image);
        LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.movie_container);
//
            TextView mMovieTitle = (TextView) titleLayout.findViewById(R.id.movie_title);
            mMovieTitle.setText("holder");
//
            TextView mMovieRating = (TextView) view.findViewById(R.id.movie_rating);
           String rating = "10";
            mMovieRating.setText(rating);
//
            TextView mReleaseDate = (TextView) view.findViewById(R.id.movie_release_date);
//            String[] splitDate = mMovie.getReleaseDate().split("-");
//           mReleaseDate.setText(splitDate[0]);
//
            TextView mOverView = (TextView) view.findViewById(R.id.movie_overview);
            mOverView.setText("holder");
//
//            Picasso.with(getActivity())
//                    .load(mMovie.getPosterPath())
//                    .placeholder(R.drawable.popcorntime)
//                    .into(imageItem);


    }
}
