package com.example.jamie.popularmovies;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MainMovieFragment;

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

    public static String getSharedPreference(Context mContext){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortBy = pref.getString(mContext.getString(R.string.pref_sort_key), mContext.getString(R.string.pref_default_sort_value));
        return sortBy;
    }
    public static Uri getUrlByIdForType(String movieID, String type) {
        //http://api.themoviedb.org/3/movie/284052/reviews?&api_key=02a6d79992ed3e3da1f638dec4c74770
        String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/"+movieID+"/"+type;

        String MOVIE_API_KEY = "api_key";
        String API_KEY = Utility.MOVIE_API_KEY;

        Uri mPopularUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(MOVIE_API_KEY, API_KEY).build();
        return mPopularUri;
    }

    public static void addMovieToFavorite(Context context, int movie_id) {
        Uri uri = MovieContract.MovieEntry.buildMovieUri(movie_id);
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.IS_FAVORITE, 1);
        context.getContentResolver().update(
                uri, //uri
                values,//content values
                MovieContract.MovieEntry.MOVIE_ID+" = ?",//where
                new String[]{Integer.toString(movie_id)}//args
        );
    }

    public static void removeMovieToFavorite(Context context, int movie_id) {
        Uri uri = MovieContract.MovieEntry.buildMovieUri(movie_id);
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.IS_FAVORITE, 0);
        context.getContentResolver().update(
                uri, //uri
                values,//content values
                MovieContract.MovieEntry.MOVIE_ID+" = ?",//where
                new String[]{Integer.toString(movie_id)}//args
        );
    }

    private static Uri getFirstMovieFromPreference(Context context, String preference) {

        Cursor cur = null;
        switch(preference){
            case MovieContract.MovieEntry.TOP_RATED:{
                cur = context.getContentResolver().query(
                        MovieContract.MovieEntry.buildTopRatedUri(),
                        null,
                        MovieContract.MovieEntry.TOP_RATED+" = 1",
                        null,
                        null
                );
                break;
            }
            case MovieContract.MovieEntry.FAVORITE:{
                cur = context.getContentResolver().query(
                        MovieContract.MovieEntry.buildFavoriteUri(),
                        null,
                        MovieContract.MovieEntry.FAVORITE+" = 1",
                        null,
                        null
                );
                break;
            }
            case MovieContract.MovieEntry.MOST_POPULAR: {
                cur = context.getContentResolver().query(
                        MovieContract.MovieEntry.buildPopularUri(),
                        null,
                        MovieContract.MovieEntry.MOST_POPULAR + " = 1",
                        null,
                        null
                );
                break;
            }
            default:
                return null;
        }

        if(cur.moveToFirst()){
            return MovieContract.MovieEntry.buildMovieUri(cur.getInt(MainMovieFragment.COL_MOVIE_ID));
        }else{
            return null;
        }
    }
}
