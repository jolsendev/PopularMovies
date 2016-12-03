package com.example.jamie.popularmovies.extracting_json_data;

import android.content.ContentValues;
import android.content.Context;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Jamie Olsen on 11/26/2016.
 */
public class ExtractJSONReviewData {
    String jsonString;
    Context mContext;
    public ExtractJSONReviewData(String jsonString, Context mContext) {
        this.mContext = mContext;
        this.jsonString = jsonString;
    }

    public void getReviewDataFromJsonAndPutInDatabase() {

        final String MOVIE_ID = "id";
        final String REVIEW_RESULTS = "results";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";
        final String REVIEW_URL = "url";

        try {

            JSONObject jsonData = new JSONObject(jsonString);
            Vector<ContentValues> cVVector = new Vector(jsonData.length());
            long movie_id = jsonData.getLong(MOVIE_ID);
            JSONArray itemsArray = jsonData.getJSONArray(REVIEW_RESULTS);
            for(int i = 0; i < itemsArray.length(); i++){
                ContentValues reviewValues = new ContentValues();
                JSONObject jObj = itemsArray.getJSONObject(i);
                reviewValues.put(ReviewEntry.MOVIE_ID, movie_id);
                reviewValues.put(ReviewEntry.REVIEW_AUTHOR, jObj.getString(REVIEW_AUTHOR));
                reviewValues.put(ReviewEntry.REVIEW_CONTENT, jObj.getString(REVIEW_CONTENT));
                reviewValues.put(ReviewEntry.REVIEW_URL, jObj.getString(REVIEW_URL));
                cVVector.add(reviewValues);
            }

            int inserted = 0;
            //add to database
            if ( cVVector.size() > 0 ) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(ReviewEntry.CONTENT_URI, cvArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
