package com.example.jamie.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;

/**
 * Created by jamie on 11/16/16.
 */

public class MovieProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final int ALL_MOVIES = 100;
    public static final int MOVIE = 200;
    public static final int MOVIE_WITH_TRAILERS = 300;
    public static final int MOVIE_WITH_REVIEWS = 400;
    //static final int FAVORITE_MOVIES = 500;

    public MovieDBHelper movieDBHelper;


    private static final SQLiteQueryBuilder sMovieTrailerQueryBuilder;

    static{

        sMovieTrailerQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sMovieTrailerQueryBuilder.setTables(
                MovieEntry.TABLE_NAME + " INNER JOIN " +
                        TrailerEntry.TABLE_NAME +
                        " ON " +  MovieEntry.TABLE_NAME +
                        "." +  MovieEntry.MOVIE_ID +
                        " = " +  TrailerEntry.TABLE_NAME +
                        "." + TrailerEntry.MOVIE_ID);
    }


    private static final SQLiteQueryBuilder sMovieReviewQueryBuilder;

    static{
        sMovieReviewQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sMovieReviewQueryBuilder.setTables(
                MovieEntry.TABLE_NAME + " INNER JOIN " +
                        ReviewEntry.TABLE_NAME +
                        " ON " +  MovieEntry.TABLE_NAME +
                        "." +  MovieEntry.MOVIE_ID +
                        " = " +  ReviewEntry.TABLE_NAME +
                        "." + ReviewEntry.MOVIE_ID);
    }
    private static final String sMovieWithReviewsSelection =
            ReviewEntry.TABLE_NAME+
                    "."+ ReviewEntry.MOVIE_ID + " = ?";

    private static final String sMovieWithTrailersSelection =
            TrailerEntry.TABLE_NAME+
                    "."+ TrailerEntry.MOVIE_ID + " = ?";

    public static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        final String PATH_TO_ALL_MOVIES = MovieEntry.TABLE_NAME;
        final String PATH_TO_MOVIE = MovieEntry.TABLE_NAME+"/#";
        final String PATH_TO_MOVIE_WITH_VIDEOS = PATH_TO_MOVIE+"/"+MovieContract.PATH_TRAILER;
        final String PATH_TO_MOVIE_WITH_REVIEWS = PATH_TO_MOVIE+"/"+MovieContract.PATH_REVIEW;

        matcher.addURI(authority, PATH_TO_ALL_MOVIES, ALL_MOVIES);
        matcher.addURI(authority, PATH_TO_MOVIE , MOVIE);
        matcher.addURI(authority,  PATH_TO_MOVIE_WITH_VIDEOS, MOVIE_WITH_TRAILERS);
        matcher.addURI(authority, PATH_TO_MOVIE_WITH_REVIEWS, MOVIE_WITH_REVIEWS);


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
            case ALL_MOVIES:
                return MovieEntry.CONTENT_TYPE;
            case MOVIE:
                return MovieEntry.CONTENT_ITEM_TYPE;
            case MOVIE_WITH_REVIEWS:
                return ReviewEntry.CONTENT_TYPE;
            case MOVIE_WITH_TRAILERS:
                return TrailerEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Uri was jacked... er something.. ");
        }

    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCurser = null;
        switch(sUriMatcher.match(uri)){
            case ALL_MOVIES:{

                retCurser = movieDBHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME, //table name
                        projection,            //columns
                        selection,             //where
                        selectionArgs,         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                break;
            }

            case MOVIE_WITH_REVIEWS:{
                System.out.println("WHY AM I HERE??");
                retCurser = getMovieWithReviews(uri, projection, sortOrder);
                break;
            }

            case MOVIE_WITH_TRAILERS:{
                retCurser = getMovieWithTrailers(uri, projection, sortOrder);
                break;
            }
        }
        retCurser.setNotificationUri(getContext().getContentResolver(), uri);
        return retCurser;
    }

    private Cursor getMovieWithTrailers(Uri uri, String[] projection, String sortOrder) {
        String movie_id = MovieEntry.getMovieIdFromPath(uri);
        Cursor retCursor;
        retCursor = sMovieTrailerQueryBuilder.query(
                movieDBHelper.getReadableDatabase(),
                projection,
                sMovieWithTrailersSelection,
                new String[]{movie_id},
                null,
                null,
                sortOrder
        );
        return retCursor;
    }

    private Cursor getMovieWithReviews(Uri uri, String[] projection, String sortOrder) {
        String movie_id = MovieEntry.getMovieIdFromPath(uri);
        Cursor retCursor;
        retCursor = sMovieReviewQueryBuilder.query(
                movieDBHelper.getReadableDatabase(),
                projection,
                sMovieWithReviewsSelection,
                new String[]{movie_id},
                null,
                null,
                sortOrder
        );
        return retCursor;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        switch (match){
            case ALL_MOVIES:{
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
            case ALL_MOVIES: {
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
            case ALL_MOVIES: {
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
