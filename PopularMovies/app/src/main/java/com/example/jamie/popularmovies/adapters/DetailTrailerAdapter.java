package com.example.jamie.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jamie.popularmovies.R;
import com.example.jamie.popularmovies.Utility;
import com.example.jamie.popularmovies.fragments.MovieDetailFragment;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

/**
 * Created by a5w5nzz on 11/30/2016.
 */

public class DetailTrailerAdapter extends CursorAdapter {

    public DetailTrailerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    private static final String GOOGLE_API_KEY = Utility.GOOGLE_API_KEY;

    private final String BASE_URL= "http://www.youtube.com/watch?v=";
    private final String BASE_IMAGE_URL = "https://img.youtube.com/vi/";
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_list_item, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final Context mContext = context;
        String source = cursor.getString(MovieDetailFragment.COL_TRAILER_SOURCE);
        int intIndex = cursor.getInt(MovieDetailFragment.COL_TRAILER_INDEX);
        String index = Integer.toString(intIndex);
        String path = BASE_IMAGE_URL+source+"/"+index+".jpg";

        TextView trailerView = (TextView) view.findViewById(R.id.movie_trailer);
        String detail = cursor.getString(MovieDetailFragment.COL_TRAILER_NAME);
        final String video_source = cursor.getString(MovieDetailFragment.COL_TRAILER_SOURCE);
        trailerView.setText(detail);

        ImageView imageItem = (ImageView) view.findViewById(R.id.trailer_thumbnail);
        Picasso.with(context)
                .load(path).placeholder(R.drawable.frame)
                .into(imageItem);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, GOOGLE_API_KEY, video_source);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(mContext) == YouTubeInitializationResult.SUCCESS){
                    mContext.startActivity(intent);
                }else{
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(BASE_URL + video_source));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                    mContext.startActivity(webIntent);
                }
            }
        });
    }
}
