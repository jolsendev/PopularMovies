package com.example.jamie.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Movie;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.jamie.popularmovies.MovieDetailView;
import com.example.jamie.popularmovies.Utility;
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
    public static final int REVIEWS = 500;
    public static final int TRAILERS = 600;
    public static final int TOP_RATED = 700;
    public static final int FAVORITE = 800;
    public static final int POPULAR = 900;
    public static final int MOVIE_DETAILS = 150;
    //static final int FAVORITE_MOVIES = 500;

    public MovieDBHelper movieDBHelper;

    //    SELECT movie.original_title, trailers.trailer_key, trailers.trailer_site, trailers.trailer_type
    //    FROM movie
    //    INNER JOIN trailers
    //    ON movie.movie_id = trailers.movie_id;
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


//movie.movie_id = reviews.movie_id and  trailers.movie_id = movie.movie_id  and  movie.movie_id = ?

//    SELECT Table4.company, table1.id, table1.value
//    FROM Table1
//    INNER JOIN Table1
//    ON Table2.table1_id = Table1.id
//    INNER JOIN Table3
//    ON Table3.table2_id = Table2.id
//    INNER JOIN Table4
//    ON Table4.table3_id = Table3.id
    private static final SQLiteQueryBuilder sMovieDetailsQueryBuilder;

    static{

        sMovieDetailsQueryBuilder = new SQLiteQueryBuilder();
        sMovieDetailsQueryBuilder.setTables(
                MovieEntry.TABLE_NAME+
                        " INNER JOIN "+ReviewEntry.TABLE_NAME+
                        " ON " + MovieEntry.TABLE_NAME + "."+MovieEntry.MOVIE_ID+" = "+ReviewEntry.TABLE_NAME+"."+
                        ReviewEntry.MOVIE_ID+
                        " INNER JOIN "+TrailerEntry.TABLE_NAME+" ON "+
                        MovieEntry.TABLE_NAME+"."+MovieEntry.MOVIE_ID+" = "+
                        TrailerEntry.TABLE_NAME+"."+TrailerEntry.MOVIE_ID

        );
    }

    //    SELECT movie.original_title, reviews.review_content
    //    FROM mo
    //    INNER JOIN reviews
    //    ON movie.movie_id = reviews.movie_id;
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
    private static final String sMovieWithId =
            MovieEntry.TABLE_NAME+"."+ReviewEntry.MOVIE_ID+" = ?";
    //movie.movie_id = reviews.movie_id and  trailers.movie_id = movie.movie_id  and  movie.movie_id = ?

    private static final String sMovieDetailsTables =
            MovieEntry.TABLE_NAME+"."+MovieEntry.MOVIE_ID+" = ?";

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
        final String PATH_TO_TRAILERS = TrailerEntry.TABLE_NAME;
        final String PATH_TO_REVIEWS = ReviewEntry.TABLE_NAME;
        final String PATH_TO_FAVORITE = MovieEntry.TABLE_NAME+"/"+MovieEntry.FAVORITE;
        final String PATH_TO_TOP_RATED = MovieEntry.TABLE_NAME+"/"+MovieEntry.TOP_RATED;
        final String PATH_TO_MOST_POPULAR = MovieEntry.TABLE_NAME+"/"+MovieEntry.MOST_POPULAR;
        final String PATH_TO_MOVIE_DETAILS = PATH_TO_MOVIE+"/"+MovieEntry.MOVIE_DETAIL;


        matcher.addURI(authority, PATH_TO_ALL_MOVIES, ALL_MOVIES);
        matcher.addURI(authority, PATH_TO_MOVIE , MOVIE);
        matcher.addURI(authority, PATH_TO_MOVIE_WITH_VIDEOS, MOVIE_WITH_TRAILERS);
        matcher.addURI(authority, PATH_TO_MOVIE_WITH_REVIEWS, MOVIE_WITH_REVIEWS);
        matcher.addURI(authority, PATH_TO_TRAILERS, TRAILERS);
        matcher.addURI(authority, PATH_TO_REVIEWS, REVIEWS);
        matcher.addURI(authority, PATH_TO_FAVORITE, FAVORITE);
        matcher.addURI(authority, PATH_TO_MOST_POPULAR, POPULAR);
        matcher.addURI(authority, PATH_TO_TOP_RATED, TOP_RATED);
        matcher.addURI(authority, PATH_TO_MOVIE_DETAILS, MOVIE_DETAILS);


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
            case TRAILERS:
                return TrailerEntry.CONTENT_TYPE;
            case REVIEWS:
                return ReviewEntry.CONTENT_TYPE;
            case FAVORITE:
                return MovieEntry.CONTENT_TYPE;
            case TOP_RATED:
                return MovieEntry.CONTENT_TYPE;
            case POPULAR:
                return MovieEntry.CONTENT_TYPE;
            case MOVIE_DETAILS:
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

            case MOVIE_DETAILS:{
                retCurser = getMovieDetails(uri, projection, sortOrder);
                DatabaseUtils.dumpCursorToString(retCurser);
                break;

            }

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
                retCurser = getMovieWithReviews(uri, projection, sortOrder);
                break;
            }

            case MOVIE_WITH_TRAILERS:{
                retCurser = getMovieWithTrailers(uri, projection, sortOrder);
                break;
            }
            case TRAILERS:{
                retCurser = movieDBHelper.getReadableDatabase().query(
                        TrailerEntry.TABLE_NAME, //table name
                        projection,            //columns
                        selection,             //where
                        selectionArgs,         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                break;
            }
            case REVIEWS:{
                retCurser = movieDBHelper.getReadableDatabase().query(
                        ReviewEntry.TABLE_NAME, //table name
                        projection,            //columns
                        selection,             //where
                        selectionArgs,         //you may include a ? in 'selection' these are the args
                        null,                  //Group by
                        null,                  //Having
                        sortOrder
                );
                break;
            }
        }
        retCurser.setNotificationUri(getContext().getContentResolver(), uri);
        return retCurser;
    }

    private Cursor getMovieDetails(Uri uri, String[] projection, String sortOrder) {
        String movie_id = MovieEntry.getMovieIdFromPath(uri);
        Cursor retCursor;
        retCursor = sMovieDetailsQueryBuilder.query(
                movieDBHelper.getReadableDatabase(),
                projection,
                sMovieDetailsTables,
                new String[]{movie_id},
                null,
                null,
                sortOrder
        );
        return retCursor;
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
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TRAILERS:{
                long _id = movieDBHelper.getWritableDatabase().insert(TrailerEntry.TABLE_NAME, null, values);
                if(_id> 0){
                    _id = (long)values.get(TrailerEntry.MOVIE_ID);
                    returnUri = MovieEntry.buildMovieTrailer(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case REVIEWS:{
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
            case TRAILERS:{
                rowDeleted = movieDBHelper.getWritableDatabase().delete(TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case REVIEWS:{
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
            case TRAILERS:{
                rowUpdated = movieDBHelper.getWritableDatabase().update(TrailerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case REVIEWS:{
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
            case REVIEWS:{
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
            case TRAILERS:{
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
