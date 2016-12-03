package com.example.jamie.popularmovies.extracting_json_data;

import android.content.ContentValues;
import android.content.Context;

import com.example.jamie.popularmovies.data.MovieContract.MovieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by a5w5nzz on 9/16/2016
 */
public class ExtractJSONMovieData {
    String jsonString;
    Context mContext;
    private ArrayList<ContentValues> mMovieContentValues;

    public ExtractJSONMovieData(String jsonString, Context mContext) {
        this.mContext = mContext;
        this.jsonString = jsonString;
    }

    public void extractMovieAndPlaceInDatabase() {
        final String MOVIE_RESULTS = "results";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_ADULT = "adult";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_ID = "id";
        final String MOVIE_ORIGINAL__TITLE = "original_title";
        final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
        final String MOVIE_TITLE = "title";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_VIDEO = "video";
        final String MOVIE_VOTE_AVERAGE= "vote_average";


        try {
            JSONObject jsonData = new JSONObject(jsonString);
            JSONArray itemsArray = jsonData.getJSONArray(MOVIE_RESULTS);
            Vector<ContentValues> cVVector = new Vector(itemsArray.length());
            for(int i = 0; i < itemsArray.length(); i++){//
                ContentValues movieValues = new ContentValues();
                JSONObject jsonMovieData = itemsArray.getJSONObject(i);
                movieValues.put(MovieEntry.POSTER_PATH, jsonMovieData.getString(MOVIE_POSTER_PATH));
                boolean isAdult = jsonMovieData.getBoolean(MOVIE_ADULT);
                movieValues.put(MovieEntry.IS_ADULT,(isAdult == true)?1:0 );
                movieValues.put(MovieEntry.OVERVIEW, jsonMovieData.getString(MOVIE_OVERVIEW));
                movieValues.put(MovieEntry.RELEASE_DATE, jsonMovieData.getString(MOVIE_RELEASE_DATE));
                movieValues.put(MovieEntry.MOVIE_ID,jsonMovieData.getInt(MOVIE_ID));
                movieValues.put(MovieEntry.ORIGINAL_TITLE, jsonMovieData.getString(MOVIE_ORIGINAL__TITLE));
                movieValues.put(MovieEntry.ORIGINAL_LANGUAGE, jsonMovieData.getString(MOVIE_ORIGINAL_LANGUAGE));
                movieValues.put(MovieEntry.TITLE, jsonMovieData.getString(MOVIE_TITLE));
                movieValues.put(MovieEntry.BACKDROP_PATH, jsonMovieData.getString(MOVIE_BACKDROP_PATH));
                movieValues.put(MovieEntry.POPULARITY, jsonMovieData.getDouble(MOVIE_POPULARITY));
                movieValues.put(MovieEntry.VOTE_COUNT, jsonMovieData.getDouble(MOVIE_VOTE_COUNT));
                boolean isMovie = jsonMovieData.getBoolean(MOVIE_VIDEO);
                movieValues.put(MovieEntry.IS_VIDEO, (isMovie == true)?1:0);
                movieValues.put(MovieEntry.VOTE_AVERAGE, jsonMovieData.getDouble(MOVIE_VOTE_AVERAGE));
                cVVector.add(movieValues);
            }

            int inserted = 0;
            //add to database
            if ( cVVector.size() > 0 ) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];

                cVVector.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, cvArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
