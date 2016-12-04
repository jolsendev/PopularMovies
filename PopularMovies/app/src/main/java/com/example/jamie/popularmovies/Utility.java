package com.example.jamie.popularmovies;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.example.jamie.popularmovies.data.MovieContract;

/**
 * Created by a5w5nzz on 9/16/2016.
 */
public final class Utility {
    public static final String MOVIE_KEY = "movie";
    public static final String MOVIE_API_KEY = BuildConfig.API_KEY;
    private static String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w185/";

    public static String getImagePath(String imageID){
        return IMAGE_BASE_PATH+imageID;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isMovieIdInDB(int movie_id, Context mContext) {
        Uri uri = MovieContract.MovieEntry.buildMovieUri(movie_id);
        Cursor cursor = mContext.getContentResolver().query(
                uri,
                new String[] {MovieContract.MovieEntry.MOVIE_ID},
                MovieContract.MovieEntry.MOVIE_ID + " = ? ",
                new String[] {Integer.toString(movie_id)},
                null,
                null);
        if(cursor.getCount() > 0){
            return true;
        }
        return false;
    }
}
