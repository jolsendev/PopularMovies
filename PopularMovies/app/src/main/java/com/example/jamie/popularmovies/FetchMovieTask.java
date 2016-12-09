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

    Context mContext;
        public FetchMovieTask(Context mContext){
            this.mContext = mContext;
        }

    @Override
    protected void onPostExecute(String movieJson) {
        super.onPostExecute(movieJson);


        //Is this super weird....
        FetchReviewTask reviewTask = new FetchReviewTask(mContext);
        reviewTask.execute(movieJson);

        FetchTrailerTask trailerTask = new FetchTrailerTask(mContext);
        trailerTask.execute(movieJson);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
        protected String doInBackground(String... params) {
            FetchRawData mRawData = new FetchRawData(params[0]);
            ExtractJSONMovieData mData = new ExtractJSONMovieData(mRawData.fetch(), mContext);
            mData.extractMovieAndPlaceInDatabase();
            return mRawData.getJsonData();
        }
}
