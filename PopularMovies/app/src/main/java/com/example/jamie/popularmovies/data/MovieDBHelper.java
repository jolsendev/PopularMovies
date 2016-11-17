package com.example.jamie.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by jamie on 11/16/16.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int VERSION_ID = 2;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_MOVIE_DATABASE = "CREATE TABLE "+ MovieEntry.TABLE_NAME+" ("+
                MovieEntry.MOVIE_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.POSTER_PATH + " TEXT NOT NULL, " +
                // 1 if true 0 if false
                MovieEntry.IS_ADULT + " INTEGER NOT NULL, " +
                MovieEntry.OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.ORIGIONAL_TITLE + " TEXT NOT NULL, " +
                MovieEntry.ORIGIONAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieEntry.TITLE + " TEXT NOT NULL, " +
                MovieEntry.BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.POPULARITY + " REAL NOT NULL, " +
                MovieEntry.VOTE_COUNT + " REAL NOT NULL, " +
                // 1 if true 0 if false
                MovieEntry.IS_VIDEO + " INTEGER NOT NULL, " +
                MovieEntry.VOTE_AVERAGE + " REAL NOT NULL, );";

                //What are my constraints!?!?!?!?

        db.execSQL(CREATE_MOVIE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
