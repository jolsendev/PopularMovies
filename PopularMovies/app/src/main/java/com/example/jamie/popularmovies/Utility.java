package com.example.jamie.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.fragments.MainMovieFragment;

/**
 * Created by a5w5nzz on 9/16/2016.
 */
public final class Utility {
    public static final String MOVIE_API_KEY = BuildConfig.API_KEY;
    private static String BASE_PATH = "http://image.tmdb.org/t/p/";
    private static String IMAGE_BASE_PATH = BASE_PATH+"/w185/";
    private static String BACKDROP_PATH = BASE_PATH+"/w342/";

    

    public static String getImagePath(String imageID){
        return IMAGE_BASE_PATH+imageID;
    }

    public static String getSharedPreference(Context mContext){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortBy = pref.getString(mContext.getString(R.string.pref_sort_key), mContext.getString(R.string.pref_default_sort_value));
        return sortBy;
    }
    public static Uri getUrlByIdForType(String movieID, String type) {
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

    public static String getImagePathBackDrop(String string) {
        return BACKDROP_PATH + string;
    }

    public static boolean isReviewInDatabase(int movieId, Context mContext) {
        return isInDataBase(MovieContract.ReviewEntry.TABLE_NAME, movieId, mContext);
    }

    public static boolean isTrailerInDatabase(long movieId, Context mContext) {
        return isInDataBase(MovieContract.TrailerEntry.TABLE_NAME, movieId, mContext);
    }

    public static boolean isMovieInDatabase(long movie_id, Context mContext) {
        return isInDataBase(MovieContract.MovieEntry.TABLE_NAME, movie_id,mContext);
    }

    private static boolean isInDataBase(String tableName, long movie_id, Context mContext) {
        Uri uri = null;
        switch (tableName){
            case MovieContract.MovieEntry.TABLE_NAME:{
                uri = MovieContract.MovieEntry.buildMovieUri(movie_id);
                break;
            }
            case MovieContract.ReviewEntry.TABLE_NAME:{
                uri = MovieContract.MovieEntry.buildMovieReview(movie_id);
                break;
            }
            case MovieContract.TrailerEntry.TABLE_NAME:{
                uri = MovieContract.MovieEntry.buildMovieTrailer(movie_id);
                break;
            }
        }
        Cursor cur = mContext.getContentResolver().query(
                uri,
                null,
                MovieContract.MovieEntry.MOVIE_ID+" = "+movie_id,
                null,
                null
        );
        if(cur != null){
            if(cur.moveToFirst()){
                return true;
            }else{
                return false;
            }
        }else
            return false;

    }
    public static Uri getFirstMovieFromPreference(Context context, String preference) {

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
