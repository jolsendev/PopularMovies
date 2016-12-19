package com.example.jamie.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.jamie.popularmovies.Utility;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;

/**
 * Created by jamie on 11/16/16.
 */

public class MovieProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final int ALL_MOVIES = 100;
    public static final int MOVIE = 200;
    public static final int MOVIE_WITH_TRAILERS = 300;
    public static final int MOVIE_WITH_REVIEWS = 400;
    public static final int ALL_REVIEWS = 500;
    public static final int ALL_TRAILERS = 600;
    public static final int TOP_RATED = 700;
    public static final int FAVORITE = 800;
    public static final int POPULAR = 900;
    //static final int FAVORITE_MOVIES = 500;

    public MovieDBHelper movieDBHelper;


    private static final String sMovieWithId =
            MovieEntry.TABLE_NAME+"."+ReviewEntry.MOVIE_ID+" = ?";
    private static final String sMovieWithReviewsSelection =
            ReviewEntry.TABLE_NAME+
                    "."+ ReviewEntry.MOVIE_ID + " = ?";
    private static final String sFavoriteMovies =
            MovieEntry.TABLE_NAME+
                    "."+ MovieEntry.IS_FAVORITE+" = 1";
    private static final String sTopRatedMovies =
            MovieEntry.TABLE_NAME+
                    "."+ MovieEntry.IS_TOP_RATED+" = 1";
    private static final String sMostPopularMovies =
            MovieEntry.TABLE_NAME+
                    "."+ MovieEntry.IS_MOST_POPULAR+" = 1";
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
        final String PATH_TO_FAVORITE = MovieEntry.TABLE_NAME+"/"+MovieEntry.FAVORITE;
        final String PATH_TO_TOP_RATED = MovieEntry.TABLE_NAME+"/"+MovieEntry.TOP_RATED;
        final String PATH_TO_MOST_POPULAR = MovieEntry.TABLE_NAME+"/"+MovieEntry.MOST_POPULAR;
        final String PATH_TO_ALL_REVIEWS = ReviewEntry.TABLE_NAME;
        final String PATH_TO_ALL_TRAILERS = TrailerEntry.TABLE_NAME;


        matcher.addURI(authority, PATH_TO_ALL_MOVIES, ALL_MOVIES);
        matcher.addURI(authority, PATH_TO_MOVIE , MOVIE);
        matcher.addURI(authority, PATH_TO_MOVIE_WITH_VIDEOS, MOVIE_WITH_TRAILERS);
        matcher.addURI(authority, PATH_TO_MOVIE_WITH_REVIEWS, MOVIE_WITH_REVIEWS);
        matcher.addURI(authority, PATH_TO_FAVORITE, FAVORITE);
        matcher.addURI(authority, PATH_TO_MOST_POPULAR, POPULAR);
        matcher.addURI(authority, PATH_TO_TOP_RATED, TOP_RATED);
        matcher.addURI(authority, PATH_TO_ALL_REVIEWS, ALL_REVIEWS);
        matcher.addURI(authority, PATH_TO_ALL_TRAILERS, ALL_TRAILERS);


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
            case FAVORITE:
                return MovieEntry.CONTENT_TYPE;
            case TOP_RATED:
                return MovieEntry.CONTENT_TYPE;
            case POPULAR:
                return MovieEntry.CONTENT_TYPE;
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
                String movie_id = MovieEntry.getMovieIdFromPath(uri);
                retCurser = movieDBHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        sMovieWithId,
                        new String[]{movie_id},
                        null,
                        null,
                        sortOrder
                );
                DatabaseUtils.dumpCursorToString(retCurser);

                break;
            }
            case TOP_RATED:{

                retCurser = movieDBHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME, //table name
                        projection,            //columns
                        sTopRatedMovies,       //where
                        selectionArgs,         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                break;
            }
            case FAVORITE:{

                retCurser = movieDBHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME, //table name
                        projection,            //columns
                        sFavoriteMovies,       //where
                        selectionArgs,         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                break;
            }
            case POPULAR:{
                retCurser = movieDBHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME, //table name
                        projection,            //columns
                        sMostPopularMovies,    //where
                        selectionArgs,         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                break;
            }
            case ALL_MOVIES:{
                String sortBy = Utility.getSharedPreference(getContext());

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
                String movie_id = MovieEntry.getMovieIdFromPath(uri);
                retCurser = movieDBHelper.getReadableDatabase().query(
                        ReviewEntry.TABLE_NAME, //table name
                        projection,            //columns
                        sMovieWithReviewsSelection,             //where
                        new String[]{movie_id},         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                //retCurser = getMovieWithReviews(uri, projection, sortOrder);
                break;
            }

            case MOVIE_WITH_TRAILERS:{
                String movie_id = MovieEntry.getMovieIdFromPath(uri);
                retCurser = movieDBHelper.getReadableDatabase().query(
                        TrailerEntry.TABLE_NAME, //table name
                        projection,            //columns
                        sMovieWithTrailersSelection,             //where
                        new String[]{movie_id},         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                //retCurser = getMovieWithTrailers(uri, projection, sortOrder);
                String holder = DatabaseUtils.dumpCursorToString(retCurser);
                break;
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
            case ALL_MOVIES:{
                long _id = movieDBHelper.getWritableDatabase().insert(MovieEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri = MovieEntry.buildMovieUri(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ALL_TRAILERS:{
                long _id = movieDBHelper.getWritableDatabase().insert(TrailerEntry.TABLE_NAME, null, values);
                if(_id> 0){
                    _id = (long)values.get(TrailerEntry.MOVIE_ID);
                    returnUri = MovieEntry.buildMovieTrailer(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ALL_REVIEWS:{
                long _id = movieDBHelper.getWritableDatabase().insert(ReviewEntry.TABLE_NAME, null, values);
                if(_id> 0){
                    _id = (long)values.get(ReviewEntry.MOVIE_ID);
                    returnUri = MovieEntry.buildMovieReview(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowDeleted = 0;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match){
            case ALL_MOVIES: {
                rowDeleted = movieDBHelper.getWritableDatabase().delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case ALL_TRAILERS:{
                rowDeleted = movieDBHelper.getWritableDatabase().delete(TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case ALL_REVIEWS:{
                rowDeleted = movieDBHelper.getWritableDatabase().delete(ReviewEntry.TABLE_NAME, selection, selectionArgs);
            }
        }
        // Because a null deletes all rows
        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowUpdated = 0;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case ALL_MOVIES: {
                rowUpdated = movieDBHelper.getWritableDatabase().update(MovieEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }
            case ALL_TRAILERS:{
                rowUpdated = movieDBHelper.getWritableDatabase().update(TrailerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case ALL_REVIEWS:{
                rowUpdated = movieDBHelper.getWritableDatabase().update(ReviewEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ALL_MOVIES: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case ALL_REVIEWS:{
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ReviewEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            }
            case ALL_TRAILERS:{
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(TrailerEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            }
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
