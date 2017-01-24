package com.example.jamie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;

/**
 * Created by jamie on 11/16/16.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    private static final int VERSION_ID = 4;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_MOVIE_DATABASE = "CREATE TABLE "+ MovieEntry.TABLE_NAME+" ( "+
                MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieEntry.MOVIE_ID + " INTEGER UNIQUE NOT NULL, "+
                MovieEntry.POSTER_PATH + " TEXT NOT NULL, " +
                MovieEntry.IS_ADULT + " INTEGER NOT NULL, " +
                MovieEntry.OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieEntry.ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieEntry.TITLE + " TEXT NOT NULL, " +
                MovieEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.POPULARITY + " REAL NOT NULL, " +
                MovieEntry.VOTE_COUNT + " REAL NOT NULL, " +
                MovieEntry.IS_VIDEO + " INTEGER NOT NULL, " +
                MovieEntry.IS_FAVORITE + " INTEGER , " +
                MovieEntry.IS_TOP_RATED + " INTEGER, " +
                MovieEntry.IS_MOST_POPULAR + " INTEGER, " +
                MovieEntry.VOTE_AVERAGE + " REAL NOT NULL, "+
                " FOREIGN KEY (" + MovieEntry.MOVIE_ID  + ") REFERENCES " +
                ReviewEntry.TABLE_NAME + " (" + ReviewEntry.MOVIE_ID + "), " +
                " FOREIGN KEY (" + MovieEntry.MOVIE_ID  + ") REFERENCES " +
                TrailerEntry.TABLE_NAME + " (" + TrailerEntry.MOVIE_ID + ")" +
                ")" +";";


        final String CREATE_TRAILER_DATABASE = "CREATE TABLE " + TrailerEntry.TABLE_NAME+" ("+
                TrailerEntry._ID + " INTEGER PRIMARY KEY," +
                TrailerEntry.MOVIE_ID + " INTEGER NOT NULL, "+
                TrailerEntry.INDEX + " INTEGER NOT NULL, "+
                TrailerEntry.TRAILER_NAME + " TEXT NOT NULL, " +
                TrailerEntry.TRAILER_SIZE + " TEXT NOT NULL, "+
                TrailerEntry.TRAILER_SOURCE + " TEXT UNIQUE NOT NULL, " +
                TrailerEntry.TRAILER_TYPE + " TEXT NOT NULL );";


        final String CREATE_REVIEW_DATABASE = "CREATE TABLE " +ReviewEntry.TABLE_NAME+" ("+
                ReviewEntry._ID + " INTEGER PRIMARY KEY," +
                ReviewEntry.MOVIE_ID + " INTEGER NOT NULL, "+
                ReviewEntry.REVIEW_AUTHOR + " TEXT NOT NULL, "+
                ReviewEntry.REVIEW_URL + " TEXT NOT NULL, "+
                ReviewEntry.REVIEW_CONTENT + " TEXT UNIQUE NOT NULL);";

        db.execSQL(CREATE_MOVIE_DATABASE);

        db.execSQL(CREATE_TRAILER_DATABASE);

        db.execSQL(CREATE_REVIEW_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);
        onCreate(db);
    }
}
