package com.example.jamie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.TrailerEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;

/**
 * Created by jamie on 11/16/16.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    private static final int VERSION_ID = 5;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_MOVIE_DATABASE = "CREATE TABLE "+ MovieEntry.TABLE_NAME+" ( "+
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                MovieEntry.MOVIE_ID + " INTEGER NOT NULL, "+
                MovieEntry.POSTER_PATH + " TEXT NOT NULL, " +
                // 1 if true 0 if false
                MovieEntry.IS_ADULT + " INTEGER NOT NULL, " +
                MovieEntry.OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieEntry.ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieEntry.TITLE + " TEXT NOT NULL, " +
                MovieEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.POPULARITY + " REAL NOT NULL, " +
                MovieEntry.VOTE_COUNT + " REAL NOT NULL, " +
                // 1 if true 0 if false
                MovieEntry.IS_VIDEO + " INTEGER NOT NULL, " +
                // 1 if true 0 if false
                MovieEntry.IS_FAVORITE + " INTEGER NOT NULL, " +

                MovieEntry.VOTE_AVERAGE + " REAL NOT NULL, "+

                // Set up the review column as a foreign key to location table.
                " FOREIGN KEY (" + MovieEntry._ID  + ") REFERENCES " +
                ReviewEntry.TABLE_NAME + " (" + ReviewEntry.MOVIE_ID + "), " +
                // Set up the video column as a foreign key to location table.
                " FOREIGN KEY (" + MovieEntry._ID  + ") REFERENCES " +
                TrailerEntry.TABLE_NAME + " (" + MovieContract.TrailerEntry.MOVIE_ID + "))" +";";

        final String CREATE_VIDEO_DATABASE = "CREATE TABLE " + TrailerEntry.TABLE_NAME+" ("+
                MovieContract.TrailerEntry.MOVIE_ID + " INTEGER PRIMARY KEY,"+
                MovieContract.TrailerEntry.VIDEO_NAME + " TEXT NOT NULL, " +
                TrailerEntry.VIDEO_SITE + " TEXT NOT NULL, " +
                MovieContract.TrailerEntry.VIDEO_SIZE + " INTEGER NOT NULL, "+
                TrailerEntry.VIDEO_TYPE + " TEXT NOT NULL );";


        final String CREATE_REVIEW_DATABASE = "CREATE TABLE " +ReviewEntry.TABLE_NAME+" ("+
                ReviewEntry.MOVIE_ID + " INTEGER PRIMARY KEY, "+
                ReviewEntry.REVIEW_AUTHOR + " TEXT NOT NULL, "+
                ReviewEntry.REVIEW_URL + " TEXT NOT NULL, "+
                ReviewEntry.REVIEW_CONTENT + " TEXT NOT NULL );"

                ;


        db.execSQL(CREATE_MOVIE_DATABASE);

        db.execSQL(CREATE_VIDEO_DATABASE);

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
