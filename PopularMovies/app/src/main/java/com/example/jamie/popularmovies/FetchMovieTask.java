package com.example.jamie.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONMovieData;

/**
 * Created by Jamie Olsen on 11/26/2016.
 */
public class FetchMovieTask extends AsyncTask<String, Void, String> {

    private final String sortBy;
    Context mContext;
        public FetchMovieTask(Context mContext, String sortBy){
            this.sortBy = sortBy;
            this.mContext = mContext;
        }

    @Override
    protected void onPostExecute(String movieJson) {
        super.onPostExecute(movieJson);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
        protected String doInBackground(String... params) {
            FetchRawData mRawData = new FetchRawData(params[0]);
            ExtractJSONMovieData mData = new ExtractJSONMovieData(mRawData.fetch(),sortBy, mContext);
            mData.extractMovieAndPlaceInDatabase();
            return mRawData.getJsonData();
        }
}
