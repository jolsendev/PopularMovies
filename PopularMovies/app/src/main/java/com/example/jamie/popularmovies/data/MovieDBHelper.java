package com.example.jamie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;
import com.example.jamie.popularmovies.data.MovieContract.VideoEntry;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;

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
                " FOREIGN KEY (" + MovieEntry.MOVIE_ID  + ") REFERENCES " +
                ReviewEntry.TABLE_NAME + " (" + ReviewEntry.REVIEW_KEY+ "), " +
                // Set up the video column as a foreign key to location table.
                " FOREIGN KEY (" + MovieEntry.MOVIE_ID  + ") REFERENCES " +
                VideoEntry.TABLE_NAME + " (" + VideoEntry.VIDEO_KEY+ "))" +";";

        final String CREATE_VIDEO_DATABASE = "CREATE TABLE " + VideoEntry.TABLE_NAME+" ("+
                VideoEntry.VIDEO_KEY + " INTEGER PRIMARY KEY,"+
                VideoEntry.VIDEO_NAME + " TEXT NOT NULL, " +
                VideoEntry.VIDEO_SITE + " TEXT NOT NULL, " +
                VideoEntry.VIDEO_SIZE + " INTEGER NOT NULL, "+
                VideoEntry.VIDEO_TYPE + " TEXT NOT NULL );";


        final String CREATE_REVIEW_DATABASE = "CREATE TABLE " +ReviewEntry.TABLE_NAME+" ("+
                ReviewEntry.REVIEW_KEY + " INTEGER PRIMARY KEY, "+
                ReviewEntry.REVIEW_AUTHOR + " TEXT NOT NULL, "+
                ReviewEntry.REVIEW_CONTENT + " TEXT NOT NULL );";


        db.execSQL(CREATE_MOVIE_DATABASE);

        db.execSQL(CREATE_VIDEO_DATABASE);

        db.execSQL(CREATE_REVIEW_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
