package com.example.jamie.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONTrailerData;

/**
 * Created by jamie on 12/4/16.
 */

public class FetchTrailerTask extends AsyncTask<String, Void, Void> {
    Context mContext;

    public FetchTrailerTask(Context mContext) {
        this.mContext = mContext;
    }

    final String MOVIE_RESULTS = "results";
    final String MOVIE_ID = "id";
    @Override
    protected Void doInBackground(String... params) {
        FetchRawData mRawData = new FetchRawData(params[0]);
        ExtractJSONTrailerData mData = new ExtractJSONTrailerData(mRawData.fetch(), mContext);
        mData.putTrailersInDatabase();
        return null;
    }
}
