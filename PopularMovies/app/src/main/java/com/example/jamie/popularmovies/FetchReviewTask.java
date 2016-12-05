package com.example.jamie.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.data.MovieContract.ReviewEntry;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONReviewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jamie on 12/4/16.
 */

public class FetchReviewTask extends AsyncTask<String, Void, Void> {
    Context mContext;


    String rawData;

    public FetchReviewTask(Context mContext) {
        this.mContext = mContext;
    }
    final String MOVIE_RESULTS = "results";
    final String MOVIE_ID = "id";
    @Override
    protected Void doInBackground(String... params) {
        //Fetch raw data (review) for each movie_id in params[0]
        String movieJsonData = params[0];

        try {
            JSONObject movieJsonObject = new JSONObject(movieJsonData);
            JSONArray itemsArray = movieJsonObject.getJSONArray(MOVIE_RESULTS);
            for(int i = 0; i < itemsArray.length(); i++){

                JSONObject jsonMovieData = itemsArray.getJSONObject(i);
                String movieID = jsonMovieData.getString(MOVIE_ID);
                Uri reviewUri = Utility.getUrlByIdForType(movieID,ReviewEntry.TABLE_NAME);
                rawData = new FetchRawData(reviewUri.toString()).fetch();

                JSONObject reviews = new JSONObject(rawData);
                ExtractJSONReviewData fetchReviewTask = new ExtractJSONReviewData(reviews.toString(), mContext);
                fetchReviewTask.getReviewDataFromJsonAndPutInDatabase();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
