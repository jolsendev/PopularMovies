package com.example.jamie.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONReviewData;

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
        FetchRawData mRawData = new FetchRawData(params[0]);
        ExtractJSONReviewData mData = new ExtractJSONReviewData(mRawData.fetch(), mContext);
        mData.getReviewDataFromJsonAndPutInDatabase();
        return null;
    }
}
