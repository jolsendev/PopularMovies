package com.example.jamie.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.jamie.popularmovies.Movie;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by jamie on 11/16/16.
 */

public class MovieProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int MOVIE = 100;
    static final int MOVIE_WITH_VIDEO = 101;
    static final int MOVIE_WITH_REVIEWS = 102;

    public MovieDBHelper movieDBHelper;



    static UriMatcher buildUriMatcher(){
        // needs to be implemented.
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieEntry.MOVIE_ID, MOVIE);
        //matcher.addURI(authority, MovieEntry. + "/*", WEATHER_WITH_LOCATION);
        //matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);

        //matcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        movieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case MOVIE:
                return MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Uri was jacked... er something.. ");
        }

    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCurser = null;
        switch(sUriMatcher.match(uri)){
            case MOVIE:{
                SQLiteQueryBuilder mbuilder = new SQLiteQueryBuilder();
                retCurser = movieDBHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            }


        }
        retCurser.setNotificationUri(getContext().getContentResolver(), uri);
        return retCurser;
    }



    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        switch (match){
            case MOVIE:{
                long _id = movieDBHelper.getWritableDatabase().insert(MovieEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri = MovieEntry.buildMovieUri(_id);
                }
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowDeleted = 0;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIE: {
                rowDeleted = movieDBHelper.getWritableDatabase().delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowUpdated = 0;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIE: {
                rowUpdated = movieDBHelper.getWritableDatabase().update(MovieEntry.TABLE_NAME,values, selection, selectionArgs);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

}
