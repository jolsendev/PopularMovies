package com.example.jamie.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.jamie.popularmovies.data.FetchRawData;
import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.extracting_json_data.ExtractJSONReviewData;

/**
 * Created by jamie on 12/4/16.
 */

public class FetchReviewTask extends AsyncTask<String, Void, Uri>{
    Context mContext;

    public FetchReviewTask(Context mContext) {
        this.mContext = mContext;
    }

    public interface Callback {

    void RestartReviewLoader();

    }
    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);
       ((Callback) mContext).RestartReviewLoader();

    }

    @Override
    protected Uri doInBackground(String... params) {
        FetchRawData mRawData = new FetchRawData(params[0]);
        ExtractJSONReviewData mData = new ExtractJSONReviewData(mRawData.fetch(), mContext);
        mData.getReviewDataFromJsonAndPutInDatabase();
        long movie_id = mData.getMovieId();
        return MovieContract.MovieEntry.buildMovieUri(movie_id);
    }
}
